package com.github.kimcore.neis.entities

enum class SchoolType(val text: String, internal val endpoint: String) {
    UNKNOWN("알수없음", "unknown"),
    ELEMENTARY("초등학교", "elsTimetable"),
    MIDDLE("중학교", "misTimetable"),
    HIGH("고등학교", "hisTimetable"),
    SPECIAL("특수학교", "spsTimetable");

    companion object {
        fun from(text: String): SchoolType {
            if (text.startsWith("각종학교")) {
                return when (text.removeSurrounding("각종학교(", ")")) {
                    "초" -> ELEMENTARY
                    "중" -> MIDDLE
                    "고" -> HIGH
                    else -> UNKNOWN
                }
            }

            return values().find { it.text == text } ?: UNKNOWN
        }
    }
}