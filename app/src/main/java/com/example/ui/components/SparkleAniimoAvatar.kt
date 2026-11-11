package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.data.model.Aniimo
import com.example.data.model.RegionalVariant
import com.example.data.model.SparkleType
import com.example.data.repository.SparkleRepository

@Composable
fun SparkleAniimoAvatar(
    aniimo: Aniimo,
    sparkleType: SparkleType,
    modifier: Modifier = Modifier,
    size: Dp = 96.dp,
    customHueOffset: Float = 0f,
    regionalVariant: RegionalVariant? = null,
    isPrismanaFullBodyShift: Boolean = true,
    showAura: Boolean = true,
    animateGlow: Boolean = true
) {
    val context = LocalContext.current
    val resId = getAniimoFaceDrawable(aniimo.id, aniimo.faceDrawableResName, context)

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

    val speciesLook = remember(aniimo.id, sparkleType.id, regionalVariant?.id) {
        SparkleRepository.getSpeciesSparkleLook(aniimo, sparkleType, regionalVariant)
    }

    val infiniteTransition = rememberInfiniteTransition(label = "SparklePulse")
    val pulseAlpha by if (animateGlow) {
        infiniteTransition.animateFloat(
            initialValue = 0.45f,
            targetValue = 0.95f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1500, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "GlowPulse"
        )
    } else {
        rememberInfiniteTransition(label = "Static").animateFloat(
            initialValue = 0.75f,
            targetValue = 0.75f,
            animationSpec = infiniteRepeatable(tween(1000)),
            label = "StaticVal"
        )
    }

    val rotationAngle by if (animateGlow) {
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 9000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "HaloRotation"
        )
    } else {
        rememberInfiniteTransition(label = "StaticRot").animateFloat(
            initialValue = 0f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(tween(1000)),
            label = "StaticRotVal"
        )
    }

    val colorFilter = remember(aniimo.id, sparkleType.id, regionalVariant?.id, isPrismanaFullBodyShift, customHueOffset) {
        ColorFilter.colorMatrix(
            SparkleRepository.createInGameSparkleColorMatrix(
                aniimo = aniimo,
                sparkleType = sparkleType,
                regionalVariant = regionalVariant,
                isPrismanaFullBodyShift = isPrismanaFullBodyShift
            )
        )
    }

    Box(
        modifier = modifier.size(size + if (showAura) 18.dp else 0.dp),
        contentAlignment = Alignment.Center
    ) {
        if (showAura) {
            // Animated Aura Ring with Species-Specific In-Game Sparkle Colors
            Canvas(
                modifier = Modifier
                    .size(size + 14.dp)
                    .rotate(rotationAngle)
            ) {
                drawCircle(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            speciesLook.signatureColorPrimary.copy(alpha = pulseAlpha),
                            speciesLook.signatureColorAccent.copy(alpha = 0.28f),
                            speciesLook.signatureColorSecondary.copy(alpha = pulseAlpha),
                            speciesLook.signatureColorPrimary.copy(alpha = pulseAlpha)
                        )
                    ),
                    style = Stroke(width = 3.2.dp.toPx())
                )
            }

            // Outer Soft Halo
            Box(
                modifier = Modifier
                    .size(size + 10.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                speciesLook.signatureColorPrimary.copy(alpha = 0.30f * pulseAlpha),
                                Color.Transparent
                            )
                        )
                    )
            )
        }

        // Inner Avatar Box with In-Game 3D Model + Sparkling Star-Mote Overlay
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(Color(0xFF0F172A))
                .border(
                    width = 2.2.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            speciesLook.signatureColorPrimary,
                            speciesLook.signatureColorSecondary
                        )
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = imageRequest,
                contentDescription = "${speciesLook.inGameTitle} Sparkle Model",
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                colorFilter = colorFilter,
                placeholder = if (resId != 0) painterResource(id = resId) else null,
                error = if (resId != 0) painterResource(id = resId) else null,
                fallback = if (resId != 0) painterResource(id = resId) else null
            )

            // In-Game Exhibition Wall 4-Point Starlight Motes & Prism Glints Overlay
            Canvas(modifier = Modifier.size(size)) {
                val w = this.size.width
                val h = this.size.height
                val starColor = speciesLook.signatureColorAccent.copy(alpha = (0.65f + 0.30f * pulseAlpha).coerceIn(0f, 1f))
                val coreGold = Color(0xFFFFFFFF).copy(alpha = 0.90f)

                drawFourPointStar(
                    center = Offset(w * 0.22f, h * 0.24f),
                    radius = w * 0.085f * pulseAlpha,
                    color = starColor,
                    coreColor = coreGold
                )
                drawFourPointStar(
                    center = Offset(w * 0.78f, h * 0.28f),
                    radius = w * 0.070f * (1.35f - 0.35f * pulseAlpha),
                    color = speciesLook.signatureColorPrimary.copy(alpha = 0.85f),
                    coreColor = coreGold
                )
                drawFourPointStar(
                    center = Offset(w * 0.26f, h * 0.74f),
                    radius = w * 0.065f * pulseAlpha,
                    color = speciesLook.signatureColorSecondary.copy(alpha = 0.85f),
                    coreColor = coreGold
                )
                if (sparkleType.isSpecial || sparkleType.id == "prototype") {
                    drawFourPointStar(
                        center = Offset(w * 0.72f, h * 0.70f),
                        radius = w * 0.075f * pulseAlpha,
                        color = starColor,
                        coreColor = coreGold
                    )
                }
            }
        }

        // Sparkle Star Indicator Badge
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 2.dp, y = 2.dp)
                .size(if (size > 60.dp) 24.dp else 18.dp)
                .clip(CircleShape)
                .background(Color(0xFF0F172A))
                .border(1.dp, speciesLook.signatureColorPrimary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (sparkleType.isSpecial) Icons.Default.AutoAwesome else Icons.Default.Star,
                contentDescription = "${sparkleType.code} Sparkle Icon",
                tint = speciesLook.signatureColorAccent,
                modifier = Modifier.size(if (size > 60.dp) 14.dp else 11.dp)
            )
        }
    }
}

private fun DrawScope.drawFourPointStar(
    center: Offset,
    radius: Float,
    color: Color,
    coreColor: Color
) {
    val inner = radius * 0.26f
    val path = Path().apply {
        moveTo(center.x, center.y - radius)
        lineTo(center.x + inner, center.y - inner)
        lineTo(center.x + radius, center.y)
        lineTo(center.x + inner, center.y + inner)
        lineTo(center.x, center.y + radius)
        lineTo(center.x - inner, center.y + inner)
        lineTo(center.x - radius, center.y)
        lineTo(center.x - inner, center.y - inner)
        close()
    }
    drawPath(path = path, color = color)
    drawCircle(color = coreColor, radius = inner * 0.75f, center = center)
}
