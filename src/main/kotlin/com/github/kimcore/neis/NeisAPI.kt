package com.github.kimcore.neis

import com.github.kimcore.neis.entities.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor

@Suppress("unused")
class NeisAPI(
    val key: String? = null
) {
    companion object {
        internal val dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd").withZone(ZoneId.of("Asia/Seoul"))
    }

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }
    private val client = HttpClient {
        defaultRequest {
            url("https://open.neis.go.kr/hub/")
        }
        install(ContentNegotiation) {
            register(
                ContentType.Any,
                KotlinxSerializationConverter(json)
            )
        }
    }

    suspend fun searchSchool(schoolName: String): List<School> {
        return searchSchool {
            parameter("SCHUL_NM", schoolName)
        }
    }

    suspend fun searchSchoolByCode(officeCode: String, schoolCode: String): School? {
        return searchSchool {
            parameter("ATPT_OFCDC_SC_CODE", officeCode)
            parameter("SD_SCHUL_CODE", schoolCode)
        }.firstOrNull()
    }

    private suspend fun searchSchool(builder: HttpRequestBuilder.() -> Unit): List<School> {
        val endpoint = "schoolInfo"
        val json = request(endpoint, builder)
        return json.decodeRow(endpoint)
    }

    suspend fun getMajors(officeCode: String, schoolCode: String): List<Major> {
        val endpoint = "schoolMajorinfo"
        val json = request(endpoint) {
            parameter("ATPT_OFCDC_SC_CODE", officeCode)
            parameter("SD_SCHUL_CODE", schoolCode)
        }
        return json.decodeRow(endpoint)
    }

    suspend fun getMeals(
        officeCode: String,
        schoolCode: String,
        type: MealType = MealType.ALL,
        date: TemporalAccessor? = null,
        from: TemporalAccessor? = null,
        to: TemporalAccessor? = null
    ): List<Meal> {
        val endpoint = "mealServiceDietInfo"
        val json = request(endpoint) {
            parameter("ATPT_OFCDC_SC_CODE", officeCode)
            parameter("SD_SCHUL_CODE", schoolCode)

            if (date != null)
                parameter("MLSV_YMD", dateFormat.format(date))

            if (from != null)
                parameter("MLSV_FROM_YMD", dateFormat.format(from))

            if (to != null)
                parameter("MLSV_TO_YMD", dateFormat.format(to))

            if (type != MealType.ALL)
                parameter("MMEAL_SC_CODE", type.ordinal)
        }

        return json.decodeRow(endpoint)
    }

    suspend fun getTimetable(
        officeCode: String,
        schoolCode: String,
        schoolType: SchoolType,
        from: TemporalAccessor = startOfWeek(),
        to: TemporalAccessor = endOfWeek(),
        grade: Int? = null,
        classNumber: Int? = null,
        major: String? = null,
    ): List<Timetable> {
        val endpoint = schoolType.endpoint
        val json = request(endpoint) {
            parameter("ATPT_OFCDC_SC_CODE", officeCode)
            parameter("SD_SCHUL_CODE", schoolCode)
            parameter("TI_FROM_YMD", dateFormat.format(from))
            parameter("TI_TO_YMD", dateFormat.format(to))

            if (grade != null)
                parameter("GRADE", grade)

            if (classNumber != null)
                parameter("CLASS_NM", classNumber)

            if (major != null)
                parameter("DDDEP_NM", major)
        }
        return json.decodeRow(endpoint)
    }

    private suspend fun request(endpoint: String, builder: HttpRequestBuilder.() -> Unit): JsonObject {
        try {
            return client.get(endpoint) {
                accept(ContentType.Any)
                if (key != null)
                    parameter("Key", key)
                parameter("Type", "json")
                builder()
            }.body()
        } catch (t: Throwable) {
            throw NEISException("$endpoint 엔드포인트에 요청을 보내던 중 오류가 발생했습니다.", null, t)
        }
    }

    private inline fun <reified T: NEISEntity> JsonObject.decodeRow(endpoint: String): List<T> {
        try {
            return this[endpoint]?.jsonArray?.get(1)?.jsonObject?.get("row")?.jsonArray?.map {
                val jsonObject = it.jsonObject
                json.decodeFromString<T>(jsonObject.toString()).apply {
                    neis = this@NeisAPI
                    raw = jsonObject
                }
            } ?: emptyList()
        } catch (t: Throwable) {
            throw NEISException("$endpoint 엔드포인트의 데이터를 파싱하던 중 오류가 발생했습니다.", this, t)
        }
    }
}