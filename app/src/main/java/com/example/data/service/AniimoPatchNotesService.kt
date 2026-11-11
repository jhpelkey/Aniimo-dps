package com.example.data.service

import android.util.Log
import com.example.data.model.FirestoreLivePatchNote
import com.example.data.model.SkillBalanceChange
import com.example.data.model.StatBalanceChange
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.Schema
import com.google.firebase.ai.type.generationConfig
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

private const val TAG = "AniimoPatchNotesService"

class AniimoPatchNotesService(
    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()
) {
    // Structured JSON Schema for parsing raw patch notes into structured balances and releases
    private val patchSchema = Schema.obj(
        properties = mapOf(
            "version" to Schema.string(description = "Semantic version of the patch e.g. 1.2.0"),
            "title" to Schema.string(description = "Official patch title or release headline"),
            "releaseDate" to Schema.string(description = "Date string e.g. 2026-11-04"),
            "summary" to Schema.string(description = "Comprehensive summary of balance changes and new content"),
            "skillChanges" to Schema.array(
                items = Schema.obj(
                    properties = mapOf(
                        "aniimoId" to Schema.string(description = "ID or lowercase name of the affected Aniimo e.g. fulmintis"),
                        "aniimoName" to Schema.string(description = "Display name of the affected Aniimo e.g. Fulmintis"),
                        "skillName" to Schema.string(description = "Name of the skill buffed or nerfed"),
                        "oldMultiplier" to Schema.double(description = "Previous damage or healing multiplier"),
                        "newMultiplier" to Schema.double(description = "Updated damage or healing multiplier"),
                        "notes" to Schema.string(description = "Dev explanation of the adjustment")
                    ),
                    optionalProperties = emptyList()
                ),
                description = "List of skill multiplier adjustments"
            ),
            "statChanges" to Schema.array(
                items = Schema.obj(
                    properties = mapOf(
                        "aniimoId" to Schema.string(description = "ID or lowercase name of the affected Aniimo"),
                        "aniimoName" to Schema.string(description = "Display name of the affected Aniimo"),
                        "statName" to Schema.string(description = "Awakening stat: ATK, HP, BREAK, PDEF, MDEF, or REGEN"),
                        "deltaPercent" to Schema.double(description = "Percentage stat change e.g. +5.0 or -3.5"),
                        "notes" to Schema.string(description = "Dev notes on stat adjustment")
                    ),
                    optionalProperties = emptyList()
                ),
                description = "List of base stat tweaks"
            ),
            "newAniimoReleases" to Schema.array(
                items = Schema.obj(
                    properties = mapOf(
                        "id" to Schema.string(description = "Lowercase ID e.g. voltclaw"),
                        "name" to Schema.string(description = "Display name e.g. Voltclaw"),
                        "aniilogNumber" to Schema.string(description = "Official Aniilog No. e.g. No. 127"),
                        "element" to Schema.string(description = "Element name e.g. Lightning, Fire, Water, Earth, Wind, Light, Dark"),
                        "region" to Schema.string(description = "Region name: Breezy Plains or Whisperwake Isles"),
                        "role" to Schema.string(description = "Combat role e.g. Burst DPS, Break Support, Sustain Tank"),
                        "baseAtk" to Schema.integer(description = "Base attack power"),
                        "signatureSkill" to Schema.string(description = "Name of signature move"),
                        "signatureMultiplier" to Schema.double(description = "Multiplier of signature move"),
                        "forms" to Schema.array(items = Schema.string(), description = "List of supported forms e.g. Standard, Highland, Prismana, Umbral")
                    ),
                    optionalProperties = emptyList()
                ),
                description = "Newly introduced Aniimo additions to catalog"
            )
        ),
        optionalProperties = listOf("releaseDate")
    )

    private val model by lazy {
        Firebase.ai.generativeModel(
            modelName = "gemini-3.8-flash",
            generationConfig = generationConfig {
                responseMimeType = "application/json"
                responseSchema = patchSchema
            }
        )
    }

    /**
     * Connects directly to an external patch notes URL or official news feed and downloads raw text
     */
    suspend fun fetchRawPatchNotesFromUrl(url: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder().url(url).build()
            val response = httpClient.newCall(request).execute()
            if (!response.isSuccessful) {
                return@withContext Result.failure(Exception("HTTP ${response.code}: Failed to download patch notes"))
            }
            val body = response.body?.string().orEmpty()
            Result.success(body)
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching patch notes from $url", e)
            Result.failure(e)
        }
    }

    /**
     * Parses raw patch notes (downloaded or pasted by user/community) using Firebase AI Logic + Gemini
     * and transforms them into structured skill multiplier changes, base stat tweaks, and new Aniimo releases.
     */
    suspend fun parsePatchNotesWithAi(rawText: String, sourceUrl: String, syncedByUid: String): Result<FirestoreLivePatchNote> {
        return try {
            val prompt = """
                You are the official Aniimo Game Data and Balance Engineering Parser.
                Analyze the following official Aniimo patch notes / update release notes.
                Extract:
                1. Patch version and headline summary.
                2. Every skill buff, nerf, or rework (Aniimo name/id, skill name, old and new multipliers, notes).
                3. Every base stat tweak across the 6 Awakening stats (ATK, HP, BREAK, PDEF, MDEF, REGEN).
                4. Any newly released Aniimo creatures being introduced into Breezy Plains or Whisperwake Isles,
                   including their Aniilog number, element, combat role, base stats, signature skill, and available forms (Highland, Snowfield, Prismana, Umbral, etc.).

                Raw Patch Notes:
                $rawText
            """.trimIndent()

            val response = withTimeout(60_000L) {
                model.generateContent(prompt)
            }

            val jsonString = response.text ?: throw IllegalStateException("Gemini returned empty response for patch notes.")
            val json = JSONObject(jsonString)

            val version = json.optString("version", "1.2.5")
            val title = json.optString("title", "Aniimo Update v$version")
            val releaseDate = json.optString("releaseDate", "2026-11-04")
            val summary = json.optString("summary", "Official Aniimo balance update & new releases.")

            val skillChangesArray = json.optJSONArray("skillChanges") ?: JSONArray()
            val statChangesArray = json.optJSONArray("statChanges") ?: JSONArray()
            val newAniimoArray = json.optJSONArray("newAniimoReleases") ?: JSONArray()

            val patchNote = FirestoreLivePatchNote(
                id = "v${version.replace(".", "_")}",
                version = version,
                title = title,
                releaseDate = releaseDate,
                sourceUrl = sourceUrl,
                summary = summary,
                skillChangesJson = skillChangesArray.toString(),
                statChangesJson = statChangesArray.toString(),
                newAniimoJson = newAniimoArray.toString(),
                syncedByUid = syncedByUid
            )

            Result.success(patchNote)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Failed to parse patch notes with Firebase AI", e)
            Result.failure(e)
        }
    }

    /**
     * Helper to decode skill changes from JSON
     */
    fun decodeSkillChanges(jsonStr: String): List<SkillBalanceChange> {
        if (jsonStr.isBlank()) return emptyList()
        return try {
            val array = JSONArray(jsonStr)
            (0 until array.length()).map { i ->
                val obj = array.getJSONObject(i)
                SkillBalanceChange(
                    aniimoId = obj.optString("aniimoId"),
                    aniimoName = obj.optString("aniimoName"),
                    skillName = obj.optString("skillName"),
                    oldMultiplier = obj.optDouble("oldMultiplier", 1.0),
                    newMultiplier = obj.optDouble("newMultiplier", 1.0),
                    notes = obj.optString("notes")
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Helper to decode stat changes from JSON
     */
    fun decodeStatChanges(jsonStr: String): List<StatBalanceChange> {
        if (jsonStr.isBlank()) return emptyList()
        return try {
            val array = JSONArray(jsonStr)
            (0 until array.length()).map { i ->
                val obj = array.getJSONObject(i)
                StatBalanceChange(
                    aniimoId = obj.optString("aniimoId"),
                    aniimoName = obj.optString("aniimoName"),
                    statName = obj.optString("statName", "ATK"),
                    deltaPercent = obj.optDouble("deltaPercent", 0.0),
                    notes = obj.optString("notes")
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
