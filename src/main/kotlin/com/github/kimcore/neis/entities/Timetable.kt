package com.github.kimcore.neis.entities

import com.github.kimcore.neis.NEIS.dateFormat
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.time.LocalDate

@Serializable
data class Timetable(
    @SerialName("ATPT_OFCDC_SC_CODE")
    val officeCode: String,
    @SerialName("ATPT_OFCDC_SC_NM")
    val officeName: String,
    @SerialName("SD_SCHUL_CODE")
    val schoolCode: String,
    @SerialName("SCHUL_NM")
    val schoolName: String,
    @SerialName("AY")
    private val yearText: String,
    @SerialName("SEM")
    private val semesterText: String,
    @SerialName("ALL_TI_YMD")
    private val dateText: String,
    @SerialName("ORD_SC_NM")
    val order: String? = null,
    @SerialName("DDDEP_NM")
    val major: String? = null,
    @SerialName("GRADE")
    private val gradeText: String,
    @SerialName("CLASS_NM")
    private val classNumberText: String,
    @SerialName("CLRM_NM")
    val classroom: String? = null,
    @SerialName("PERIO")
    private val periodText: String,
    @SerialName("ITRT_CNTNT")
    val content: String
) : NEISEntity() {
    @Transient
    val date: LocalDate = LocalDate.from(dateFormat.parse(dateText))

    @Transient
    val year = yearText.toInt()

    @Transient
    val semester = semesterText.toInt()

    @Transient
    val grade = gradeText.toInt()

    @Transient
    val classNumber = classNumberText.toInt()

    @Transient
    val period = periodText.toInt()

    override fun toString(): String {
        return "Timetable(officeCode=$officeCode, officeName=$officeName, schoolCode=$schoolCode, schoolName=$schoolName, year=$year, semester=$semester, date=$date, order=$order, major=$major, grade=$grade, classNumber=$classNumber, classroom=$classroom, period=$period, content=$content)"
    }
}