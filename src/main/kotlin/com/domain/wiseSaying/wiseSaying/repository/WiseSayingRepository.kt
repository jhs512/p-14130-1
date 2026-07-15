package com.domain.wiseSaying.wiseSaying.repository

import com.domain.wiseSaying.wiseSaying.entity.WiseSaying
import com.standard.dto.Page
import com.standard.dto.paged
import com.standard.util.text.matchesLike

interface WiseSayingRepository {
    fun save(wiseSaying: WiseSaying): WiseSaying
    fun isEmpty(): Boolean
    fun findAll(): List<WiseSaying>
    fun findById(id: Int): WiseSaying?
    fun delete(wiseSaying: WiseSaying)
    fun clear()
    fun build()

    fun findByAuthorLike(authorLike: String): List<WiseSaying> =
        findAll().filter { it.author.matchesLike(authorLike) }

    fun findByContentLike(contentLike: String): List<WiseSaying> =
        findAll().filter { it.content.matchesLike(contentLike) }

    fun findAllPaged(itemsPerPage: Int, pageNo: Int): Page<WiseSaying> =
        findAll().paged(itemsPerPage, pageNo)

    fun findByKeywordPaged(keywordType: String, keyword: String, itemsPerPage: Int, pageNo: Int): Page<WiseSaying> =
        when (keywordType) {
            "author" -> findByAuthorLike("%$keyword%")
            else -> findByContentLike("%$keyword%")
        }.paged(itemsPerPage, pageNo, keywordType, keyword)
}
