package com.example.data.model

enum class SkillType(val label: String) {
    BASE_SKILL_1("Base Skill 1"),
    BASE_SKILL_2("Base Skill 2"),
    CORE_SKILL("Core Skill (Contract Enhanced)"),
    CORE_PASSIVE("Passive Trait")
}

data class AniimoSkill(
    val id: String,
    val name: String,
    val type: SkillType,
    val damageMultiplier: String,
    val cooldownSec: Int,
    val dpsPotentialTier: String, // e.g. "S-Tier", "A-Tier", "B-Tier"
    val dpsPerSec: Int, // e.g. 178000 for 178k DPS/sec
    val isHighestDps: Boolean = false,
    val isCoreSkill: Boolean = false,
    val enhancedByMaxedItem: String? = null, // e.g. "Maxed Pendant (+15)"
    val enhancedMultiplier: String? = null, // e.g. "+25% Skill Crit Rate & 850% ATK"
    val enhancedDpsPerSec: Int? = null,
    val description: String,
    val dpsSynergyEffect: String
)
