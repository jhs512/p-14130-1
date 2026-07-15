package com.standard.util.json

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test

class JsonUtilTest {
    @Test
    @DisplayName("jsonStrToMap")
    fun t1() {
        val jsonStr = """
            {
                "id": 1,
                "content": "현재를 사랑하라.",
                "author": "작자미상"
            }
        """.trimIndent()

        val map = JsonUtil.jsonStrToMap(jsonStr)

        assertThat(map["id"]).isEqualTo(1)
        assertThat(map["content"]).isEqualTo("현재를 사랑하라.")
        assertThat(map["author"]).isEqualTo("작자미상")
    }

    @Test
    @DisplayName("jsonStrToMap - 값에 쉼표가 포함된 경우")
    fun t2() {
        val jsonStr = """
            {
                "id": 2,
                "content": "삶이 그대를 속일지라도, 슬퍼하거나 노하지 말라.",
                "author": "푸시킨"
            }
        """.trimIndent()

        val map = JsonUtil.jsonStrToMap(jsonStr)

        assertThat(map["id"]).isEqualTo(2)
        assertThat(map["content"]).isEqualTo("삶이 그대를 속일지라도, 슬퍼하거나 노하지 말라.")
        assertThat(map["author"]).isEqualTo("푸시킨")
    }
}
