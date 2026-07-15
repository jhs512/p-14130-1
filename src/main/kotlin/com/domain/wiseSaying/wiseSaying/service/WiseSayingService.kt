package com.domain.wiseSaying.wiseSaying.service

import com.domain.wiseSaying.wiseSaying.entity.WiseSaying
import com.domain.wiseSaying.wiseSaying.repository.WiseSayingRepository

class WiseSayingService {
    private val wiseSayingRepository = WiseSayingRepository()

    fun write(content: String, author: String): WiseSaying =
        wiseSayingRepository.save(WiseSaying(content, author))

    fun isEmpty(): Boolean = wiseSayingRepository.isEmpty()

    fun findAll(): List<WiseSaying> = wiseSayingRepository.findAll()

    fun findById(id: Int): WiseSaying? = wiseSayingRepository.findById(id)

    fun delete(wiseSaying: WiseSaying) {
        wiseSayingRepository.delete(wiseSaying)
    }

    fun modify(wiseSaying: WiseSaying, content: String, author: String) {
        wiseSaying.modify(content, author)

        wiseSayingRepository.save(wiseSaying)
    }
}
