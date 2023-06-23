package com.github.kimcore.neis.entities

import com.github.kimcore.neis.NeisAPI.Companion.dateFormat
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.time.LocalDate

@Serializable
data class Meal(
    @SerialName("ATPT_OFCDC_SC_CODE")
    val officeCode: String,
    @SerialName("ATPT_OFCDC_SC_NM")
    val officeName: String,
    @SerialName("SD_SCHUL_CODE")
    val schoolCode: String,
    @SerialName("SCHUL_NM")
    val schoolName: String,
    @SerialName("MMEAL_SC_CODE")
    private val typeText: String,
    @SerialName("MLSV_YMD")
    private val dateText: String,
    @SerialName("DDISH_NM")
    private val rawContent: String,
    @SerialName("ORPLC_INFO")
    private val rawOrigin: String,
    @SerialName("NTR_INFO")
    private val rawNutrition: String,
    @SerialName("CAL_INFO")
    private val caloriesText: String
) : NEISEntity() {
    @Transient
    val type = MealType.values()[typeText.toInt()]

    @Transient
    val date: LocalDate = LocalDate.from(dateFormat.parse(dateText))

    @Transient
    val content = rawContent.replace("<br/>", "\n")

    @Transient
    val origin = rawOrigin.replace("<br/>", "\n")

    @Transient
    val nutrition = rawNutrition.replace("<br/>", "\n")

    @Transient
    val calories = caloriesText.removeSuffix(" Kcal").toDouble()

    override fun toString(): String {
        return "Meal(officeCode=$officeCode, officeName=$officeName, schoolCode=$schoolCode, schoolName=$schoolName, type=$type, date=$date, content=$content, origin=$origin, nutrition=$nutrition, calories=$calories)"
    }
}

