package com.halimjr11.cameo.common.helper

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class DateFormatter {

    val isoDate: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    val pretty: DateTimeFormatter =
        DateTimeFormatter.ofPattern("d MMM yyyy", Locale.getDefault())

    fun parseDate(dateString: String = "2025-10-15"): LocalDate {
        return LocalDate.parse(dateString, isoDate)
    }

    fun formatDate(date: String = LocalDate.now().toString()): String {
        val parsedDate = parseDate(date)
        return date.format(pretty)
    }

    /**
     * Convert runtime in minutes to formatted string like "1h 40m"
     */
    fun formatRuntime(minutes: Int?): String {
        if (minutes == null || minutes <= 0) return "-"
        val hours = minutes / 60
        val remainingMinutes = minutes % 60

        return buildString {
            if (hours > 0) append("${hours}h ")
            if (remainingMinutes > 0) append("${remainingMinutes}m")
        }.trim()
    }

}