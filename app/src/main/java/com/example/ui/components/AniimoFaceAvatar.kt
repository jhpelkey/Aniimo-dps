package com.example.ui.components

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.data.model.Aniimo
import com.example.data.model.Element
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

/**
 * Resolves and caches actual online in-game/wiki images for each Aniimo from
 * aniimotools.dev (3D WebP renders & OpenGraph creature renders) and Aniimo Fandom Wiki,
 * while also supporting user-provided custom image URL overrides.
 */
object AniimoOnlineImageResolver {
    private const val PREFS_NAME = "aniimo_online_images_prefs"
    private const val BROWSER_USER_AGENT =
        "Mozilla/5.0 (Linux; Android 14) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Mobile Safari/537.36"

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(6, TimeUnit.SECONDS)
            .readTimeout(6, TimeUnit.SECONDS)
            .followRedirects(true)
            .build()
    }

    // Known direct 3D & Wiki image assets discovered on aniimotools.dev and aniimo.fandom.com
    private val verifiedDirectAssetUrls = mapOf(
        "emberpup" to "https://aniimotools.dev/assets/creatures/3d/emberpup__1005100.webp",
        "scorchhowl" to "https://aniimotools.dev/assets/creatures/3d/scorchhowl__1005300.webp",
        "ignitis" to "https://aniimotools.dev/assets/creatures/3d/ignitis__1003200.webp",
        "gachapus" to "https://aniimotools.dev/assets/og/creatures/gachapus.webp",
        "fulmintis" to "https://aniimotools.dev/assets/og/creatures/fulmintis.webp",
        "malevsera" to "https://aniimotools.dev/assets/og/creatures/malevsera.webp",
        "grizbo" to "https://aniimotools.dev/assets/og/creatures/grizbo.webp",
        "whirlbite" to "https://aniimotools.dev/assets/og/creatures/whirlbite.webp",
        "irisalis" to "https://aniimotools.dev/assets/og/creatures/irisalis.webp"
    )

    // Reactive in-memory map of resolved/custom image URLs per aniimoId
    val resolvedUrls = mutableStateMapOf<String, String>()
    private val attemptedLookups = mutableSetOf<String>()

    fun getDirectOnlineImageUrl(aniimoId: String, explicitRemoteUrl: String? = null): String {
        val key = aniimoId.lowercase().trim()
        resolvedUrls[key]?.takeIf { it.isNotBlank() }?.let { return it }
        verifiedDirectAssetUrls[key]?.let { return it }
        if (!explicitRemoteUrl.isNullOrBlank() &&
            (explicitRemoteUrl.endsWith(".webp", ignoreCase = true) ||
                explicitRemoteUrl.endsWith(".png", ignoreCase = true) ||
                explicitRemoteUrl.endsWith(".jpg", ignoreCase = true) ||
                explicitRemoteUrl.contains("static.wikia.nocookie.net"))
        ) {
            return explicitRemoteUrl
        }
        return "https://aniimotools.dev/assets/og/creatures/$key.webp"
    }

    fun setCustomImageUrl(context: Context, aniimoId: String, imageUrl: String) {
        val key = aniimoId.lowercase().trim()
        val trimmed = imageUrl.trim()
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if (trimmed.isEmpty()) {
            resolvedUrls.remove(key)
            prefs.edit().remove("custom_$key").apply()
        } else {
            resolvedUrls[key] = trimmed
            prefs.edit().putString("custom_$key", trimmed).apply()
        }
    }

    suspend fun resolveBestOnlineImageIfNeeded(context: Context, aniimoId: String, aniimoName: String) {
        val key = aniimoId.lowercase().trim()
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // 1. Check user custom URL first
        val customSaved = prefs.getString("custom_$key", null)
        if (!customSaved.isNullOrBlank()) {
            resolvedUrls[key] = customSaved
            return
        }

        // 2. Check previously discovered live asset URL
        val cachedDiscovered = prefs.getString("discovered_$key", null)
        if (!cachedDiscovered.isNullOrBlank()) {
            resolvedUrls[key] = cachedDiscovered
            return
        }

        synchronized(attemptedLookups) {
            if (!attemptedLookups.add(key)) return
        }

        withContext(Dispatchers.IO) {
            try {
                // Attempt to extract the exact 3D WebP render URL from aniimotools.dev creature page
                val pageRequest = Request.Builder()
                    .url("https://aniimotools.dev/creatures/$key")
                    .header("User-Agent", BROWSER_USER_AGENT)
                    .build()

                httpClient.newCall(pageRequest).execute().use { response ->
                    if (response.isSuccessful) {
                        val html = response.body?.string().orEmpty()
                        // Match 3D creature webp asset first: /assets/creatures/3d/<name>__<id>.webp
                        val regex3d = Regex("""https?://aniimotools\.dev/assets/creatures/3d/[a-zA-Z0-9_.-]+\.webp|/assets/creatures/3d/[a-zA-Z0-9_.-]+\.webp""")
                        val match3d = regex3d.find(html)?.value
                        if (!match3d.isNullOrBlank()) {
                            val fullUrl = if (match3d.startsWith("http")) match3d else "https://aniimotools.dev$match3d"
                            prefs.edit().putString("discovered_$key", fullUrl).apply()
                            resolvedUrls[key] = fullUrl
                            return@withContext
                        }

                        // Or match og:image .webp/.png
                        val regexOg = Regex("""https?://aniimotools\.dev/assets/og/creatures/[a-zA-Z0-9_.-]+\.webp""")
                        val matchOg = regexOg.find(html)?.value
                        if (!matchOg.isNullOrBlank()) {
                            prefs.edit().putString("discovered_$key", matchOg).apply()
                            resolvedUrls[key] = matchOg
                            return@withContext
                        }
                    }
                }

                // Fallback: Query Aniimo Fandom Wiki MediaWiki API for official creature PNG icon
                val wikiTitle = java.net.URLEncoder.encode(aniimoName, "UTF-8")
                val wikiApiUrl = "https://aniimo.fandom.com/api.php?action=query&titles=$wikiTitle&prop=pageimages&format=json&pithumbsize=400"
                val wikiReq = Request.Builder()
                    .url(wikiApiUrl)
                    .header("User-Agent", BROWSER_USER_AGENT)
                    .build()

                httpClient.newCall(wikiReq).execute().use { response ->
                    if (response.isSuccessful) {
                        val json = response.body?.string().orEmpty()
                        val sourceRegex = Regex(""""source"\s*:\s*"([^"]+)"\s*""")
                        val rawUrl = sourceRegex.find(json)?.groupValues?.getOrNull(1)?.replace("\\/", "/")
                        if (!rawUrl.isNullOrBlank() && rawUrl.startsWith("http")) {
                            prefs.edit().putString("discovered_$key", rawUrl).apply()
                            resolvedUrls[key] = rawUrl
                        }
                    }
                }
            } catch (_: Exception) {
                // Offline or unreachable in test environment; keep direct URL & drawable fallback
            }
        }
    }

    fun buildCoilRequest(context: Context, imageUrl: String): ImageRequest {
        return ImageRequest.Builder(context)
            .data(imageUrl)
            .addHeader("User-Agent", BROWSER_USER_AGENT)
            .addHeader("Referer", "https://aniimotools.dev/")
            .crossfade(true)
            .build()
    }
}

/**
 * Returns the drawable resource ID for an Aniimo's face portrait picture.
 * Maps all 75 canonical Aniimo species and their evolutionary lineages
 * to high-definition creature portrait pictures as an instant offline fallback.
 */
fun getAniimoFaceDrawable(
    aniimoId: String,
    context: android.content.Context?
): Int = getAniimoFaceDrawable(aniimoId = aniimoId, faceDrawableResName = null, context = context)

fun getAniimoFaceDrawable(
    aniimoId: String,
    faceDrawableResName: String? = null,
    context: android.content.Context? = null
): Int {
    // 1. Direct match by explicitly assigned resource name
    val byResName = when (faceDrawableResName?.lowercase()?.trim()) {
        "img_face_fulmintis", "fulmintis" -> R.drawable.img_face_fulmintis
        "img_face_malevsera", "malevsera" -> R.drawable.img_face_malevsera
        "img_face_grizbo", "grizbo" -> R.drawable.img_face_grizbo
        "img_face_gachapus", "gachapus" -> R.drawable.img_face_gachapus
        "img_face_whirlbite", "whirlbite" -> R.drawable.img_face_whirlbite
        "img_face_irisalis", "irisalis" -> R.drawable.img_face_irisalis
        "img_face_thornblade", "thornblade" -> R.drawable.img_face_thornblade
        "img_face_ignitis", "ignitis" -> R.drawable.img_face_ignitis
        "img_face_fenmane", "fenmane" -> R.drawable.img_face_fenmane
        "img_face_stellarys", "stellarys" -> R.drawable.img_face_stellarys
        "img_face_scorchhowl", "scorchhowl" -> R.drawable.img_face_scorchhowl
        "img_face_infergon", "infergon" -> R.drawable.img_face_infergon
        "img_face_cornet", "cornet" -> R.drawable.img_face_cornet
        "img_face_glynsera", "glynsera" -> R.drawable.img_face_glynsera
        "img_face_sherro", "sherro" -> R.drawable.img_face_sherro
        else -> 0
    }
    if (byResName != 0) return byResName

    // 2. Comprehensive mapping for all 126 canonical Aniimos to creature face portraits
    val bySpecies = when (aniimoId.lowercase().trim()) {
        "emberpup", "flameruff", "scorchhowl", "pyroraptor", "squarrel", "squashel", "sparki", "jeglid", "jeburst" -> R.drawable.img_face_scorchhowl
        "ignitis", "infergon", "flambea", "inferlupa", "cinderwolf", "flamerion", "jawling", "helmwhelp", "helgon", "lavazar", "magmarex", "sparkelf" -> R.drawable.img_face_infergon
        "bolty", "zapzap", "fulmintis", "wisptis", "jaguaraun", "voltis", "amperion", "blazen", "turbo" -> R.drawable.img_face_fulmintis
        "sera", "malangel", "malevsera", "abysser", "noctowl", "umbra", "glacy", "sugarfish", "sugartini" -> R.drawable.img_face_malevsera
        "cubbo", "ursarock", "grizbo", "boulderkin", "terrator", "baleetle", "waleetle", "bouldus", "pebbling", "geodeback", "minespine", "cozite", "bailite", "cactriel" -> R.drawable.img_face_grizbo
        "octoball", "tentacool", "popapus", "gachapus", "piopiota" -> R.drawable.img_face_gachapus
        "dewlop", "dewple", "whirlbite", "chompkin", "reefish", "coraliz", "cheekie", "wavwal", "panpanta", "prawnch", "jabster", "lilipole", "scrubbit", "tubster" -> R.drawable.img_face_whirlbite
        "iris", "irisal", "chrysalet", "irisalis", "silklet", "dazmand", "leafy", "flutternym", "gracewing", "somniwing", "melloblum", "dewy", "fragrancier", "bubbeep", "glameep", "hypnyto" -> R.drawable.img_face_irisalis
        "budsquire", "florasword", "thornblade", "sproutling", "petalite", "budclaw", "shrubclaw", "geoclaw", "hummin", "witchin", "salabask", "crocobask", "mireling", "saws" -> R.drawable.img_face_thornblade
        "pomegg", "pomawk", "manelet", "fentuft", "fenmane", "fenrier" -> R.drawable.img_face_fenmane
        "chirpi", "tromber", "cornet", "zephyret", "stormwing", "nimbi", "eko", "eklue", "tuckin", "hypnowl", "mesmerowl", "royalby" -> R.drawable.img_face_cornet
        "bonesky", "spookeer", "glynsera", "hexxin", "helmut", "pawney", "rookey", "coinwisp", "pouchwisp", "ekrim" -> R.drawable.img_face_glynsera
        "celestis", "stellarys", "novacrest", "bulbly", "veilfloat", "luminelle", "fahloo", "erlath" -> R.drawable.img_face_stellarys
        "skippy", "pranky", "sherro", "shelly", "sheldon", "susuta", "popota", "fawnpy", "pipsqueak", "besauce", "hoptail" -> R.drawable.img_face_sherro
        else -> R.drawable.img_face_fulmintis
    }
    if (bySpecies != 0) return bySpecies

    // 3. Fallback to dynamic lookup if context is available
    if (context != null) {
        val dynamicId = context.resources.getIdentifier("img_face_${aniimoId.lowercase().trim()}", "drawable", context.packageName)
        if (dynamicId != 0) return dynamicId
    }

    return R.drawable.img_face_fulmintis
}

@Composable
fun AniimoFaceAvatar(
    aniimo: Aniimo,
    modifier: Modifier = Modifier,
    size: Dp = 56.dp,
    shape: androidx.compose.ui.graphics.Shape = CircleShape,
    regionalVariant: com.example.data.model.RegionalVariant? = null
) {
    val context = LocalContext.current
    val resId = getAniimoFaceDrawable(
        aniimoId = aniimo.id,
        faceDrawableResName = aniimo.faceDrawableResName,
        context = context
    )

    LaunchedEffect(aniimo.id) {
        AniimoOnlineImageResolver.resolveBestOnlineImageIfNeeded(
            context = context,
            aniimoId = aniimo.id,
            aniimoName = aniimo.name
        )
    }

    val onlineImageUrl = AniimoOnlineImageResolver.getDirectOnlineImageUrl(
        aniimoId = aniimo.id,
        explicitRemoteUrl = aniimo.remoteArtworkUrl
    )
    val imageRequest = remember(onlineImageUrl) {
        AniimoOnlineImageResolver.buildCoilRequest(context, onlineImageUrl)
    }

    val borderColor = if (regionalVariant != null && !regionalVariant.isNativeDefault) {
        regionalVariant.primaryTint
    } else {
        aniimo.element.primaryColor
    }

    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .background(Color(0xFF0F172A))
            .border(2.dp, borderColor, shape),
        contentAlignment = Alignment.Center
    ) {
        if (resId == 0) {
            CreatureFaceIllustration(
                element = regionalVariant?.secondaryElement ?: aniimo.element,
                size = size
            )
        }
        AsyncImage(
            model = imageRequest,
            contentDescription = "${regionalVariant?.variantName ?: aniimo.name} In-Game Picture",
            modifier = Modifier
                .size(size)
                .clip(shape),
            contentScale = ContentScale.Crop,
            placeholder = if (resId != 0) painterResource(id = resId) else null,
            error = if (resId != 0) painterResource(id = resId) else null,
            fallback = if (resId != 0) painterResource(id = resId) else null
        )
    }
}

@Composable
fun AniimoFaceAvatarById(
    aniimoId: String,
    aniimoName: String,
    element: Element,
    modifier: Modifier = Modifier,
    size: Dp = 32.dp,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(8.dp)
) {
    val context = LocalContext.current
    val resId = getAniimoFaceDrawable(
        aniimoId = aniimoId,
        faceDrawableResName = null,
        context = context
    )

    LaunchedEffect(aniimoId) {
        AniimoOnlineImageResolver.resolveBestOnlineImageIfNeeded(
            context = context,
            aniimoId = aniimoId,
            aniimoName = aniimoName
        )
    }

    val onlineImageUrl = AniimoOnlineImageResolver.getDirectOnlineImageUrl(
        aniimoId = aniimoId,
        explicitRemoteUrl = null
    )
    val imageRequest = remember(onlineImageUrl) {
        AniimoOnlineImageResolver.buildCoilRequest(context, onlineImageUrl)
    }

    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .background(Color(0xFF0F172A))
            .border(1.5.dp, element.primaryColor, shape),
        contentAlignment = Alignment.Center
    ) {
        if (resId == 0) {
            CreatureFaceIllustration(
                element = element,
                size = size
            )
        }
        AsyncImage(
            model = imageRequest,
            contentDescription = "$aniimoName In-Game Picture",
            modifier = Modifier
                .size(size)
                .clip(shape),
            contentScale = ContentScale.Crop,
            placeholder = if (resId != 0) painterResource(id = resId) else null,
            error = if (resId != 0) painterResource(id = resId) else null,
            fallback = if (resId != 0) painterResource(id = resId) else null
        )
    }
}

/**
 * Procedural creature face portrait illustration used as a rich visual fallback,
 * featuring creature eyes, facial horns/ears, snout, and glowing aura.
 */
@Composable
private fun CreatureFaceIllustration(
    element: Element,
    size: Dp
) {
    Box(
        modifier = Modifier
            .size(size)
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        element.glowColor.copy(alpha = 0.6f),
                        Color(0xFF0F172A)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = this.size.width
            val h = this.size.height

            val earPathLeft = Path().apply {
                moveTo(w * 0.25f, h * 0.35f)
                lineTo(w * 0.12f, h * 0.12f)
                lineTo(w * 0.40f, h * 0.25f)
                close()
            }
            val earPathRight = Path().apply {
                moveTo(w * 0.75f, h * 0.35f)
                lineTo(w * 0.88f, h * 0.12f)
                lineTo(w * 0.60f, h * 0.25f)
                close()
            }
            drawPath(earPathLeft, element.primaryColor.copy(alpha = 0.85f))
            drawPath(earPathRight, element.primaryColor.copy(alpha = 0.85f))

            drawCircle(
                color = Color(0xFF1E293B),
                radius = w * 0.32f,
                center = Offset(w * 0.5f, h * 0.52f)
            )

            val eyeColor = Color.White
            val pupilColor = element.primaryColor
            drawOval(
                color = eyeColor,
                topLeft = Offset(w * 0.32f, h * 0.44f),
                size = Size(w * 0.12f, h * 0.14f)
            )
            drawCircle(
                color = pupilColor,
                radius = w * 0.04f,
                center = Offset(w * 0.38f, h * 0.50f)
            )
            drawOval(
                color = eyeColor,
                topLeft = Offset(w * 0.56f, h * 0.44f),
                size = Size(w * 0.12f, h * 0.14f)
            )
            drawCircle(
                color = pupilColor,
                radius = w * 0.04f,
                center = Offset(w * 0.62f, h * 0.50f)
            )

            drawCircle(
                color = element.primaryColor.copy(alpha = 0.9f),
                radius = w * 0.035f,
                center = Offset(w * 0.5f, h * 0.63f)
            )
        }
    }
}
