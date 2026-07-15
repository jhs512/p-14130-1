package com.domain.wiseSaying.wiseSaying.repository

import com.domain.wiseSaying.wiseSaying.entity.WiseSaying

class WiseSayingRepository {
    private var lastId = 0
    private val wiseSayings = mutableListOf<WiseSaying>()

    fun save(wiseSaying: WiseSaying): WiseSaying {
        if (wiseSaying.isNew()) {
            wiseSaying.id = ++lastId
            wiseSayings.add(wiseSaying)
        }

        return wiseSaying
    }

    fun isEmpty(): Boolean = wiseSayings.isEmpty()

    fun findAll(): List<WiseSaying> = wiseSayings

    fun findById(id: Int): WiseSaying? = wiseSayings.firstOrNull { it.id == id }

    fun delete(wiseSaying: WiseSaying) {
        wiseSayings.remove(wiseSaying)
    }

    fun clear() {
        lastId = 0
        wiseSayings.clear()
    }
}
