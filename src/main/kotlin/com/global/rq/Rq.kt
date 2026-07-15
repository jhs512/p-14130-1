package com.global.rq

class Rq(cmd: String) {
    val action: String = cmd.substringBefore("?").trim()

    private val paramMap: Map<String, String> =
        cmd.substringAfter("?", missingDelimiterValue = "")
            .split("&")
            .mapNotNull { query ->
                val parts = query.split("=", limit = 2)

                if (parts.size != 2) return@mapNotNull null

                if (parts[1].isBlank()) return@mapNotNull null

                parts[0].trim() to parts[1].trim()
            }
            .toMap()

    fun getParamValue(name: String): String? =
        paramMap[name]

    fun getParamValueAsInt(name: String, default: Int): Int =
        paramMap[name]?.toIntOrNull() ?: default
}