package com.example.data.model

import androidx.compose.ui.graphics.Color

enum class Element(
    val displayName: String,
    val primaryColor: Color,
    val accentColor: Color,
    val glowColor: Color,
    val synergyTag: String
) {
    LIGHTNING(
        displayName = "Lightning",
        primaryColor = Color(0xFFFFD60A),
        accentColor = Color(0xFF64D2FF),
        glowColor = Color(0x33FFD60A),
        synergyTag = "Stun & Electro Stash"
    ),
    ICE(
        displayName = "Ice",
        primaryColor = Color(0xFF5AC8FA),
        accentColor = Color(0xFF00E5FF),
        glowColor = Color(0x335AC8FA),
        synergyTag = "Freeze & Bloodthirst"
    ),
    WATER(
        displayName = "Water",
        primaryColor = Color(0xFF0A84FF),
        accentColor = Color(0xFF5AC8FA),
        glowColor = Color(0x330A84FF),
        synergyTag = "Debuff & Torrent"
    ),
    EARTH(
        displayName = "Earth",
        primaryColor = Color(0xFFFF9F0A),
        accentColor = Color(0xFFD97706),
        glowColor = Color(0x33FF9F0A),
        synergyTag = "Rage & Might Shred"
    ),
    GRASS(
        displayName = "Grass",
        primaryColor = Color(0xFF30D158),
        accentColor = Color(0xFF34C759),
        glowColor = Color(0x3330D158),
        synergyTag = "Sword Dance & Bloom"
    ),
    FIRE(
        displayName = "Fire",
        primaryColor = Color(0xFFFF453A),
        accentColor = Color(0xFFFF9F0A),
        glowColor = Color(0x33FF453A),
        synergyTag = "Ignition & Burst"
    ),
    WIND(
        displayName = "Wind",
        primaryColor = Color(0xFF64D2FF),
        accentColor = Color(0xFF38BDF8),
        glowColor = Color(0x3364D2FF),
        synergyTag = "Gale & Swift Evade"
    ),
    LIGHT(
        displayName = "Light",
        primaryColor = Color(0xFFFFD700),
        accentColor = Color(0xFFFFFBEB),
        glowColor = Color(0x33FFD700),
        synergyTag = "Prismana & Radiant Blade"
    ),
    DARK(
        displayName = "Dark",
        primaryColor = Color(0xFFBF5AF2),
        accentColor = Color(0xFF5E5CE6),
        glowColor = Color(0x33BF5AF2),
        synergyTag = "Demonic State & Drain"
    );

    companion object {
        fun fromString(value: String): Element {
            return when (value.uppercase()) {
                "VOLT" -> LIGHTNING
                "AQUA" -> WATER
                "FLORA" -> GRASS
                "UMBRA" -> DARK
                "ASTRA" -> LIGHT
                else -> values().find { it.name.equals(value, ignoreCase = true) } ?: LIGHTNING
            }
        }
    }
}
