package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.model.CustomBuild
import com.example.data.model.Element

@Entity(tableName = "custom_builds")
data class CustomBuildEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val buildTitle: String,
    val aniimoId: String,
    val aniimoName: String,
    val aniimoElement: String,
    val partnerId: String,
    val partnerName: String,
    val partnerElement: String,
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
    val createdAt: Long
) {
    fun toDomain(): CustomBuild {
        val aniimoElem = Element.fromString(aniimoElement)
        val partnerElem = Element.fromString(partnerElement)
        return CustomBuild(
            id = id,
            buildTitle = buildTitle,
            aniimoId = aniimoId,
            aniimoName = aniimoName,
            aniimoElement = aniimoElem,
            partnerId = partnerId,
            partnerName = partnerName,
            partnerElement = partnerElem,
            skill1Level = skill1Level,
            skill2Level = skill2Level,
            ultimateLevel = ultimateLevel,
            weaponName = weaponName,
            relicSetName = relicSetName,
            calculatedDps = calculatedDps,
            burstDps = burstDps,
            elementalBonusPercent = elementalBonusPercent,
            critRatePercent = critRatePercent,
            critDmgPercent = critDmgPercent,
            createdAt = createdAt
        )
    }

    companion object {
        fun fromDomain(build: CustomBuild): CustomBuildEntity {
            return CustomBuildEntity(
                id = build.id,
                buildTitle = build.buildTitle,
                aniimoId = build.aniimoId,
                aniimoName = build.aniimoName,
                aniimoElement = build.aniimoElement.name,
                partnerId = build.partnerId,
                partnerName = build.partnerName,
                partnerElement = build.partnerElement.name,
                skill1Level = build.skill1Level,
                skill2Level = build.skill2Level,
                ultimateLevel = build.ultimateLevel,
                weaponName = build.weaponName,
                relicSetName = build.relicSetName,
                calculatedDps = build.calculatedDps,
                burstDps = build.burstDps,
                elementalBonusPercent = build.elementalBonusPercent,
                critRatePercent = build.critRatePercent,
                critDmgPercent = build.critDmgPercent,
                createdAt = build.createdAt
            )
        }
    }
}
