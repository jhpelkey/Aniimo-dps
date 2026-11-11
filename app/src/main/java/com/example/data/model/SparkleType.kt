package com.example.data.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorMatrix

/**
 * Represents an official in-game Sparkling variation from Aniimo (aniimotools.dev Exhibition Wall):
 * - Sparkling Prototype (natural creature colors + glittering Sparkling particle coat)
 * - Numbered Sparkling Types I through X (99% combined pool from Sparkling Cubes / Sigils)
 * - Dazzling Sparkling (1% from Normal Egg Sparkling Cube / 0.5% from Sparkling Sigil & Pigment)
 * - Shadow Sparkling (1% from Egg Heist Sparkling Cube / 0.5% from Sparkling Sigil & Pigment)
 */
data class SparkleType(
    val id: String,
    val code: String,              // "Prototype", "Type I" .. "Type X", "Dazzling", "Shadow"
    val name: String,              // In-game Exhibition Wall palette descriptor
    val rarity: String,            // "Base Sparkle", "Numbered (I-X)", "Hidden Mythic"
    val odds: String,              // e.g. "Wild 1/20,000 • Cube 9.9%"
    val description: String,
    val coatEffect: String,        // How the coat pigment changes in-game
    val auraEffect: String,        // In-game Sparkling star-motes & summon effect
    val primaryColor: Color,
    val secondaryColor: Color,
    val accentColor: Color,
    val hueShiftDegrees: Float,
    val isSpecial: Boolean = false,
    val badgeLabel: String = "EXHIBITION WALL SPARKLE",
    val cubeNormalOdds: String = "9.9% (99% Pool)",
    val cubeEggHeistOdds: String = "9.9% (99% Pool)",
    val sigilPigmentOdds: String = "9.9% (99% Pool)"
) {
    /**
     * Builds a realistic, non-destructive ColorMatrix that shifts midtone coat pigmentation
     * while preserving 3D render lighting, eye detail, and dark outlines—matching the
     * in-game Aniimo Exhibition Wall look rather than washing out the portrait.
     */
    fun createColorMatrix(customHueOffset: Float = 0f): ColorMatrix {
        if (id == "prototype") {
            // Sparkling Prototype keeps 100% of the creature's natural colors + glittering particle coat
            return ColorMatrix()
        }
        val totalHue = (hueShiftDegrees + customHueOffset) % 360f
        return when (id) {
            "shadow" -> {
                // Hidden Shadow Sparkling: deep obsidian-slate coat with rich violet-indigo highlights (not a harsh photo negative)
                ColorMatrix(
                    floatArrayOf(
                        0.68f, 0.06f, 0.12f, 0f, 8f,
                        0.04f, 0.62f, 0.10f, 0f, 4f,
                        0.14f, 0.08f, 0.88f, 0f, 22f,
                        0f,    0f,    0f,    1f, 0f
                    )
                )
            }
            "dazzling" -> {
                // Hidden Dazzling Sparkling: warm luminous champagne-gold & starlight sheen
                ColorMatrix(
                    floatArrayOf(
                        1.14f, 0.10f, 0.02f, 0f, 16f,
                        0.08f, 1.10f, 0.04f, 0f, 14f,
                        0.02f, 0.06f, 0.94f, 0f, 8f,
                        0f,    0f,    0f,    1f, 0f
                    )
                )
            }
            "type_8" -> {
                // Type VIII Pearl-Frost coat: soft alabaster-cyan lift
                ColorMatrix(
                    floatArrayOf(
                        0.92f, 0.08f, 0.08f, 0f, 18f,
                        0.06f, 0.96f, 0.08f, 0f, 20f,
                        0.08f, 0.10f, 1.04f, 0f, 24f,
                        0f,    0f,    0f,    1f, 0f
                    )
                )
            }
            "type_9" -> {
                // Type IX Charcoal-Garnet coat: stealthy dusk coat with warm crimson-garnet warmth
                ColorMatrix(
                    floatArrayOf(
                        0.96f, 0.06f, 0.04f, 0f, 12f,
                        0.04f, 0.76f, 0.04f, 0f, 4f,
                        0.06f, 0.04f, 0.80f, 0f, 8f,
                        0f,    0f,    0f,    1f, 0f
                    )
                )
            }
            else -> {
                // Numbered Types I–VII & X: Clean coat hue rotation blended 55% with 45% natural shading
                val angle = Math.toRadians(totalHue.toDouble())
                val cosA = Math.cos(angle).toFloat()
                val sinA = Math.sin(angle).toFloat()

                val a00 = 0.213f + cosA * 0.787f - sinA * 0.213f
                val a01 = 0.715f - cosA * 0.715f - sinA * 0.715f
                val a02 = 0.072f - cosA * 0.072f + sinA * 0.928f

                val a10 = 0.213f - cosA * 0.213f + sinA * 0.143f
                val a11 = 0.715f + cosA * 0.285f + sinA * 0.140f
                val a12 = 0.072f - cosA * 0.072f - sinA * 0.283f

                val a20 = 0.213f - cosA * 0.213f - sinA * 0.787f
                val a21 = 0.715f - cosA * 0.715f + sinA * 0.715f
                val a22 = 0.072f + cosA * 0.928f + sinA * 0.072f

                ColorMatrix(
                    floatArrayOf(
                        0.45f + a00 * 0.55f, a01 * 0.55f,         a02 * 0.55f,         0f, 6f,
                        a10 * 0.55f,         0.45f + a11 * 0.55f, a12 * 0.55f,         0f, 6f,
                        a20 * 0.55f,         a21 * 0.55f,         0.45f + a22 * 0.55f, 0f, 6f,
                        0f,                  0f,                  0f,                  1f, 0f
                    )
                )
            }
        }
    }
}
