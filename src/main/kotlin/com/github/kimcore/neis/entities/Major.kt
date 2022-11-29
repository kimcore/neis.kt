package com.github.kimcore.neis.entities

import com.github.kimcore.neis.NEIS.dateFormat
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.time.LocalDate

@Serializable
data class Major(
    @SerialName("ATPT_OFCDC_SC_CODE")
    val officeCode: String,
    @SerialName("ATPT_OFCDC_SC_NM")
    val officeName: String,
    @SerialName("SD_SCHUL_CODE")
    val schoolCode: String,
    @SerialName("SCHUL_NM")
    val schoolName: String,
    @SerialName("ORD_SC_NM")
    val order: String,
    @SerialName("DDDEP_NM")
    val name: String,
    @SerialName("LOAD_DTM")
    private val dateText: String
) : NEISEntity() {
    @Transient
    val date: LocalDate = LocalDate.from(dateFormat.parse(dateText))

    override fun toString(): String {
        return "Major(officeCode=$officeCode, officeName=$officeName, schoolCode=$schoolCode, schoolName=$schoolName, order=$order, name=$name, date=$date)"
    }
}