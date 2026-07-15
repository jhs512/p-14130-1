package com.domain.wiseSaying.wiseSaying.entity

data class WiseSaying(
    var content: String,
    var author: String,
) {
    var id: Int = 0

    fun modify(content: String, author: String) {
        this.content = content
        this.author = author
    }

    fun isNew(): Boolean = id == 0
}
