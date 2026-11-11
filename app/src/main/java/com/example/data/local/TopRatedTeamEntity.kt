package com.example.data.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.example.data.model.CapabilityPerk
import com.example.data.model.Element
import com.example.data.model.PointInvestmentStep
import com.example.data.model.TeamMemberBuild
import com.example.data.model.TeamMode
import com.example.data.model.TopRatedTeam
import kotlinx.coroutines.flow.Flow

/**
 * Room entity for Community Top Rated Teams for specific Aniimos:
 * - 4 Aniimo per team
 * - 1 Held Item per Aniimo
 * - Capability (Awakening) points invested in HP, ATK, BREAK, PDEF, MDEF, and REGEN
 * - Exact order per point invested across all 5 team modes
 */
@Entity(tableName = "top_rated_teams")
data class TopRatedTeamEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val modeString: String,
    val primaryAniimoId: String,
    val primaryAniimoName: String,
    // Formatted serialized lines for lightweight, zero-dependency serialization
    val membersRaw: String, // format: "id|name|role|element|heldItem|heldItemEffect|awakeningOrder;..."
    val capabilitiesRaw: String, // format: "statName|tier|effect|pointsInvested;..."
    val pointOrderRaw: String, // format: "range|statOrCapability|rationale|priority;..."
    val strategyGuide: String,
    val estimatedDpsOrWinRate: String,
    val author: String,
    val upvotes: Int,
    val hasUserUpvoted: Boolean,
    val isUserCreated: Boolean,
    val createdAt: Long = System.currentTimeMillis()
) {
    fun toDomain(): TopRatedTeam {
        val mode = try {
            TeamMode.valueOf(modeString)
        } catch (_: Exception) {
            TeamMode.DPS
        }

        val members = membersRaw.split(";").filter { it.isNotBlank() }.mapNotNull { memberStr ->
            val parts = memberStr.split("|")
            if (parts.size >= 5) {
                TeamMemberBuild(
                    aniimoId = parts[0],
                    aniimoName = parts[1],
                    role = parts[2],
                    element = Element.fromString(parts[3]),
                    heldItem = parts[4],
                    heldItemEffect = parts.getOrNull(5) ?: "",
                    awakeningOrder = parts.getOrNull(6) ?: "ATK > BREAK > REGEN"
                )
            } else null
        }

        val capabilities = capabilitiesRaw.split(";").filter { it.isNotBlank() }.mapNotNull { capStr ->
            val parts = capStr.split("|")
            if (parts.size >= 4) {
                CapabilityPerk(
                    name = parts[0],
                    tier = parts[1],
                    effect = parts[2],
                    costPoints = parts[3].toIntOrNull() ?: 0
                )
            } else null
        }

        val pointOrder = pointOrderRaw.split(";").filter { it.isNotBlank() }.mapNotNull { stepStr ->
            val parts = stepStr.split("|")
            if (parts.size >= 4) {
                PointInvestmentStep(
                    pointRange = parts[0],
                    capabilityOrSkill = parts[1],
                    rationale = parts[2],
                    priorityLevel = parts[3]
                )
            } else null
        }

        return TopRatedTeam(
            id = id,
            title = title,
            mode = mode,
            primaryAniimoId = primaryAniimoId,
            primaryAniimoName = primaryAniimoName,
            members = members,
            capabilities = capabilities,
            pointInvestmentOrder = pointOrder,
            strategyGuide = strategyGuide,
            estimatedDpsOrWinRate = estimatedDpsOrWinRate,
            author = author,
            upvotes = upvotes,
            hasUserUpvoted = hasUserUpvoted,
            isUserCreated = isUserCreated
        )
    }

    companion object {
        fun fromDomain(team: TopRatedTeam): TopRatedTeamEntity {
            val membersStr = team.members.joinToString(";") { m ->
                "${m.aniimoId}|${m.aniimoName}|${m.role}|${m.element.name}|${m.heldItem}|${m.heldItemEffect}|${m.awakeningOrder}"
            }
            val capStr = team.capabilities.joinToString(";") { c ->
                "${c.name}|${c.tier}|${c.effect}|${c.costPoints}"
            }
            val pointStr = team.pointInvestmentOrder.joinToString(";") { p ->
                "${p.pointRange}|${p.capabilityOrSkill}|${p.rationale}|${p.priorityLevel}"
            }

            return TopRatedTeamEntity(
                id = team.id,
                title = team.title,
                modeString = team.mode.name,
                primaryAniimoId = team.primaryAniimoId,
                primaryAniimoName = team.primaryAniimoName,
                membersRaw = membersStr,
                capabilitiesRaw = capStr,
                pointOrderRaw = pointStr,
                strategyGuide = team.strategyGuide,
                estimatedDpsOrWinRate = team.estimatedDpsOrWinRate,
                author = team.author,
                upvotes = upvotesSafe(team.upvotes),
                hasUserUpvoted = team.hasUserUpvoted,
                isUserCreated = team.isUserCreated
            )
        }

        private fun upvotesSafe(upvotes: Int): Int = upvotes.coerceAtLeast(0)
    }
}

@Dao
interface TopRatedTeamDao {
    @Query("SELECT * FROM top_rated_teams ORDER BY upvotes DESC")
    fun getAllTeams(): Flow<List<TopRatedTeamEntity>>

    @Query("SELECT * FROM top_rated_teams WHERE primaryAniimoId = :aniimoId ORDER BY upvotes DESC")
    fun getTeamsForAniimo(aniimoId: String): Flow<List<TopRatedTeamEntity>>

    @Query("SELECT * FROM top_rated_teams WHERE modeString = :mode ORDER BY upvotes DESC")
    fun getTeamsByMode(mode: String): Flow<List<TopRatedTeamEntity>>

    @Query("SELECT * FROM top_rated_teams WHERE id = :id")
    suspend fun getTeamById(id: String): TopRatedTeamEntity?

    @Query("UPDATE top_rated_teams SET upvotes = upvotes + :delta, hasUserUpvoted = :hasUpvoted WHERE id = :id")
    suspend fun toggleUpvote(id: String, delta: Int, hasUpvoted: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(team: TopRatedTeamEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(teams: List<TopRatedTeamEntity>)

    @Query("SELECT COUNT(*) FROM top_rated_teams")
    suspend fun getCount(): Int
}
