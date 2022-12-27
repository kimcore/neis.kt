package com.github.kimcore.neis

import java.time.LocalDate
import java.time.ZoneId
import java.util.*

private val calendar: Calendar
    get() = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"))
private val zone = ZoneId.of("Asia/Seoul")
fun startOfWeek(): LocalDate = LocalDate.ofInstant(calendar.apply {
    set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
}.time.toInstant(), zone)

fun endOfWeek(): LocalDate = LocalDate.ofInstant(calendar.apply {
    set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek + 6)
}.time.toInstant(), zone)