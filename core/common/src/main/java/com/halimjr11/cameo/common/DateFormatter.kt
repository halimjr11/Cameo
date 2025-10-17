package com.halimjr11.cameo.common

class DateFormatter {

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