package com.domain.wiseSaying.wiseSaying.entity

import com.standard.util.json.JsonUtil
import com.standard.util.json.JsonUtil.jsonStrToMap

data class WiseSaying(
    var id: Int = 0,
    var content: String,
    var author: String,
) {
    constructor(content: String, author: String) : this(0, content, author)

    companion object {
        fun fromJsonStr(jsonStr: String): WiseSaying {
            val map = jsonStrToMap(jsonStr)

            return WiseSaying(
                id = map["id"] as Int,
                content = map["content"] as String,
                author = map["author"] as String,
            )
        }
    }

    fun modify(content: String, author: String) {
        this.content = content
        this.author = author
    }

    fun isNew(): Boolean = id == 0

    val jsonStr: String
        get() = JsonUtil.toString(map)

    val map: Map<String, Any>
        get() = mapOf(
            "id" to id,
            "content" to content,
            "author" to author,
        )
}
