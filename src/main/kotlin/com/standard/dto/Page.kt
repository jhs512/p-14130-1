package com.standard.dto

import kotlin.math.ceil

data class Page<T>(
    val totalItems: Int,
    val itemsPerPage: Int,
    val pageNo: Int,
    val keywordType: String,
    val keyword: String,
    val content: List<T>,
) {
    // 결과가 없어도 1페이지는 존재하는 것으로 취급한다
    val totalPages = ceil(totalItems.toDouble() / itemsPerPage).toInt().coerceAtLeast(1)
}

fun <T> List<T>.paged(itemsPerPage: Int, pageNo: Int, keywordType: String = "", keyword: String = ""): Page<T> =
    Page(
        totalItems = size,
        itemsPerPage = itemsPerPage,
        pageNo = pageNo,
        keywordType = keywordType,
        keyword = keyword,
        content = drop((pageNo - 1) * itemsPerPage).take(itemsPerPage),
    )
