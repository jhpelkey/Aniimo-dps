package com.example.data.model

data class BossEncounter(
    val id: String,
    val name: String,
    val zone: String,
    val elementWeakness: Element,
    val difficulty: String,
    val recommendedDps: Int,
    val parClearTime: String,
    val description: String
)
