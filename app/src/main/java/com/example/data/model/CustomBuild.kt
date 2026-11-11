package com.example.data.model

data class CustomBuild(
    val id: Long = 0,
    val buildTitle: String,
    val aniimoId: String,
    val aniimoName: String,
    val aniimoElement: Element,
    val partnerId: String,
    val partnerName: String,
    val partnerElement: Element,
    val skill1Level: Int,
    val skill2Level: Int,
    val ultimateLevel: Int,
    val weaponName: String,
    val relicSetName: String,
    val calculatedDps: Int,
    val burstDps: Int,
    val elementalBonusPercent: Int,
    val critRatePercent: Float,
    val critDmgPercent: Float,
    val createdAt: Long = System.currentTimeMillis()
)
