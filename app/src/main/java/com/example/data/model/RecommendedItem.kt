package com.example.data.model

enum class ItemSlot(val label: String) {
    PRIMARY_HELD_ITEM("Best-in-Slot Held Item (1 Equipped)"),
    SECONDARY_HELD_ITEM("Alternative Held Item Option"),
    CONTRACT_HELD_ITEM("Signature +15 Contract Item (1 Equipped)"),
    SUPPORT_HELD_ITEM("Team Utility Held Item Option")
}

data class RecommendedItem(
    val id: String,
    val name: String,
    val slot: ItemSlot,
    val rarity: String, // "Legendary", "Epic", "Rare"
    val mainStat: String,
    val coreEffect: String,
    val dpsContribution: String,
    val isBestInSlot: Boolean,
    val sourceLocation: String
)
