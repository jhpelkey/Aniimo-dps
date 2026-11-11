package com.example.data.model

data class PartnerSynergy(
    val partnerAniimoId: String,
    val partnerName: String = partnerAniimoId.replaceFirstChar { it.uppercase() },
    val partnerElement: Element = Element.WIND,
    val synergyTier: String, // "S+", "S", "A"
    val dpsBoostPercent: Int,
    val resonanceTitle: String,
    val resonanceEffect: String,
    val partnerSkillBonus: String,
    val rotationTip: String
)
