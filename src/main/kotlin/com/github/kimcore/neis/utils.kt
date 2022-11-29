package com.github.kimcore.neis

import java.time.LocalDate
import java.util.*

private val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"))

fun startOfWeek(): LocalDate = LocalDate.from((calendar.clone() as Calendar).apply {
    set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
}.time.toInstant())

fun endOfWeek(): LocalDate = LocalDate.from((calendar.clone() as Calendar).apply {
    set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek + 6)
}.time.toInstant())