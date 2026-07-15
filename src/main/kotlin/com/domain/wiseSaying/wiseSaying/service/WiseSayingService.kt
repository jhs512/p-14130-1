package com.domain.wiseSaying.wiseSaying.service

import com.domain.wiseSaying.wiseSaying.entity.WiseSaying

class WiseSayingService {
    private var lastId = 0
    private val wiseSayings = mutableListOf<WiseSaying>()

    fun write(content: String, author: String): WiseSaying =
        WiseSaying(++lastId, content, author).also { wiseSayings.add(it) }

    fun isEmpty(): Boolean = wiseSayings.isEmpty()

    fun findAll(): List<WiseSaying> = wiseSayings

    fun findById(id: Int): WiseSaying? = wiseSayings.find { it.id == id }

    fun delete(wiseSaying: WiseSaying) {
        wiseSayings.remove(wiseSaying)
    }

    fun modify(wiseSaying: WiseSaying, content: String, author: String) {
        wiseSaying.modify(content, author)
    }
}
