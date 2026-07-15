package com.domain.wiseSaying.wiseSaying.service

import com.domain.wiseSaying.wiseSaying.entity.WiseSaying
import com.global.bean.SingletonScope
import com.standard.dto.Page

class WiseSayingService {
    private val wiseSayingRepository = SingletonScope.wiseSayingRepository

    fun write(content: String, author: String): WiseSaying =
        wiseSayingRepository.save(WiseSaying(content, author))

    fun isEmpty(): Boolean = wiseSayingRepository.isEmpty()

    fun findAllPaged(itemsPerPage: Int, pageNo: Int): Page<WiseSaying> =
        wiseSayingRepository.findAllPaged(itemsPerPage, pageNo)

    fun findByKeywordPaged(keywordType: String, keyword: String, itemsPerPage: Int, pageNo: Int): Page<WiseSaying> =
        wiseSayingRepository.findByKeywordPaged(keywordType, keyword, itemsPerPage, pageNo)

    fun findById(id: Int): WiseSaying? = wiseSayingRepository.findById(id)

    fun delete(wiseSaying: WiseSaying) {
        wiseSayingRepository.delete(wiseSaying)
    }

    fun modify(wiseSaying: WiseSaying, content: String, author: String) {
        wiseSaying.modify(content, author)

        wiseSayingRepository.save(wiseSaying)
    }

    fun build() {
        wiseSayingRepository.build()
    }
}
