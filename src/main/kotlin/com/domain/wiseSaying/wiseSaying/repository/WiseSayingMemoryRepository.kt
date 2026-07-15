package com.domain.wiseSaying.wiseSaying.repository

import com.domain.wiseSaying.wiseSaying.entity.WiseSaying
import com.standard.dto.Page

class WiseSayingMemoryRepository : WiseSayingRepository {
    private var lastId = 0
    private val wiseSayings = mutableListOf<WiseSaying>()

    override fun save(wiseSaying: WiseSaying): WiseSaying {
        if (wiseSaying.isNew()) {
            wiseSaying.id = ++lastId
            wiseSayings.add(wiseSaying)
        }

        return wiseSaying
    }

    override fun isEmpty(): Boolean = wiseSayings.isEmpty()

    override fun findAll(): List<WiseSaying> = wiseSayings.reversed()

    override fun findById(id: Int): WiseSaying? = wiseSayings.firstOrNull { it.id == id }

    override fun delete(wiseSaying: WiseSaying) {
        wiseSayings.remove(wiseSaying)
    }

    override fun clear() {
        lastId = 0
        wiseSayings.clear()
    }

    override fun build() {}

    override fun findByAuthorLike(authorLike: String): List<WiseSaying> =
        findAll().filterByLike(authorLike) { it.author }

    override fun findByContentLike(contentLike: String): List<WiseSaying> =
        findAll().filterByLike(contentLike) { it.content }

    override fun findAllPaged(itemsPerPage: Int, pageNo: Int): Page<WiseSaying> {
        val content = findAll()
            .drop((pageNo - 1) * itemsPerPage)
            .take(itemsPerPage)

        return Page(wiseSayings.size, itemsPerPage, pageNo, "", "", content)
    }

    override fun findByKeywordPaged(
        keywordType: String,
        keyword: String,
        itemsPerPage: Int,
        pageNo: Int,
    ): Page<WiseSaying> {
        val wiseSayings = when (keywordType) {
            "author" -> findByAuthorLike("%$keyword%")
            else -> findByContentLike("%$keyword%")
        }

        val content = wiseSayings
            .drop((pageNo - 1) * itemsPerPage)
            .take(itemsPerPage)

        return Page(wiseSayings.size, itemsPerPage, pageNo, keywordType, keyword, content)
    }

    private fun List<WiseSaying>.filterByLike(
        like: String,
        fieldValue: (WiseSaying) -> String,
    ): List<WiseSaying> {
        val pureKeyword = like.replace("%", "")

        if (pureKeyword.isBlank()) {
            return this
        }

        return when {
            like.startsWith("%") && like.endsWith("%") -> filter { fieldValue(it).contains(pureKeyword) }
            like.startsWith("%") -> filter { fieldValue(it).endsWith(pureKeyword) }
            like.endsWith("%") -> filter { fieldValue(it).startsWith(pureKeyword) }
            else -> filter { fieldValue(it) == pureKeyword }
        }
    }
}
