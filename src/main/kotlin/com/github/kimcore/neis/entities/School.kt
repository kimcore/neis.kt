package com.github.kimcore.neis.entities

import com.github.kimcore.neis.NEIS
import com.github.kimcore.neis.endOfWeek
import com.github.kimcore.neis.startOfWeek
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.time.temporal.TemporalAccessor

@Serializable
data class School(
    @SerialName("ATPT_OFCDC_SC_CODE")
    val officeCode: String,
    @SerialName("ATPT_OFCDC_SC_NM")
    val officeName: String,
    @SerialName("SD_SCHUL_CODE")
    val code: String,
    @SerialName("SCHUL_NM")
    val name: String,
    @SerialName("ENG_SCHUL_NM")
    val nameEn: String,
    @SerialName("SCHUL_KND_SC_NM")
    private val typeText: String,
    @SerialName("ORG_RDNMA")
    val location: String,
    @SerialName("HS_GNRL_BUSNS_SC_NM")
    val businessType: String,
    @SerialName("HMPG_ADRES")
    val homepageUrl: String
) : NEISEntity() {
    @Transient
    val type: SchoolType = SchoolType.from(typeText)
    suspend fun getMajors() = NEIS.getMajors(officeCode, code)
    suspend fun getMeals(
        type: MealType = MealType.ALL,
        date: TemporalAccessor? = null,
        from: TemporalAccessor? = null,
        to: TemporalAccessor? = null
    ) = NEIS.getMeals(officeCode, code, type, date, from, to)

    suspend fun getTimetable(
        from: TemporalAccessor = startOfWeek(),
        to: TemporalAccessor = endOfWeek(),
        grade: Int? = null,
        classNumber: Int? = null,
        major: String? = null
    ) = NEIS.getTimetable(officeCode, code, type, from, to, grade, classNumber, major)

    override fun toString(): String {
        return "School(officeCode=$officeCode, officeName=$officeName, code=$code, name=$name, nameEn=$nameEn, type=$type, location=$location, businessType=$businessType, homepageUrl=$homepageUrl)"
    }
}
