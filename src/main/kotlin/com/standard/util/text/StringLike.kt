package com.standard.util.text

// SQL LIKE 스타일 매칭: %키워드%(포함), %키워드(끝남), 키워드%(시작), 키워드(일치)
fun String.matchesLike(like: String): Boolean {
    val pureKeyword = like.replace("%", "")

    if (pureKeyword.isBlank()) return true

    return when {
        like.startsWith("%") && like.endsWith("%") -> contains(pureKeyword)
        like.startsWith("%") -> endsWith(pureKeyword)
        like.endsWith("%") -> startsWith(pureKeyword)
        else -> this == pureKeyword
    }
}
