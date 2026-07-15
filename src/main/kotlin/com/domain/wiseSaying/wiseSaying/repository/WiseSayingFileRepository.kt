package com.domain.wiseSaying.wiseSaying.repository

import com.domain.wiseSaying.wiseSaying.entity.WiseSaying
import com.global.app.AppConfig
import com.standard.dto.Page
import com.standard.util.json.JsonUtil
import java.nio.file.Path

class WiseSayingFileRepository : WiseSayingRepository {
    val tableDirPath: Path
        get() = AppConfig.dbDirPath.resolve("wiseSaying")

    override fun save(wiseSaying: WiseSaying): WiseSaying {
        if (wiseSaying.isNew()) wiseSaying.id = genNextId()

        saveOnDisk(wiseSaying)

        return wiseSaying
    }

    override fun isEmpty(): Boolean =
        tableDirPath.toFile()
            .listFiles()
            ?.filter { it.name != "data.json" }
            ?.none { it.name.endsWith(".json") }
            ?: true

    override fun findAll(): List<WiseSaying> =
        tableDirPath.toFile()
            .listFiles()
            ?.filter { it.name != "data.json" }
            ?.filter { it.name.endsWith(".json") }
            ?.map { it.readText() }
            ?.map(WiseSaying.Companion::fromJsonStr)
            ?.sortedByDescending { it.id }
            .orEmpty()

    override fun findById(id: Int): WiseSaying? =
        tableDirPath
            .resolve("$id.json")
            .toFile()
            .takeIf { it.exists() }
            ?.readText()
            ?.let(WiseSaying.Companion::fromJsonStr)

    override fun delete(wiseSaying: WiseSaying) {
        tableDirPath
            .resolve("${wiseSaying.id}.json")
            .toFile()
            .takeIf { it.exists() }
            ?.delete()
    }

    override fun clear() {
        tableDirPath.toFile().deleteRecursively()
    }

    override fun build() {
        mkTableDirsIfNotExists()

        val mapList = findAll()
            .map(WiseSaying::map)

        JsonUtil.toString(mapList)
            .let {
                tableDirPath
                    .resolve("data.json")
                    .toFile()
                    .writeText(it)
            }
    }

    private fun saveOnDisk(wiseSaying: WiseSaying) {
        mkTableDirsIfNotExists()

        val wiseSayingFile = tableDirPath.resolve("${wiseSaying.id}.json")
        wiseSayingFile.toFile().writeText(wiseSaying.jsonStr)
    }

    private fun mkTableDirsIfNotExists() {
        tableDirPath.toFile().mkdirs()
    }

    internal fun saveLastId(lastId: Int) {
        mkTableDirsIfNotExists()

        tableDirPath.resolve("lastId.txt")
            .toFile()
            .writeText(lastId.toString())
    }

    internal fun loadLastId(): Int =
        try {
            tableDirPath.resolve("lastId.txt")
                .toFile()
                .readText()
                .toInt()
        } catch (e: Exception) {
            0
        }

    private fun genNextId(): Int =
        (loadLastId() + 1).also {
            saveLastId(it)
        }

    override fun findByAuthorLike(authorLike: String): List<WiseSaying> =
        findAll().filterByLike(authorLike) { it.author }

    override fun findByContentLike(contentLike: String): List<WiseSaying> =
        findAll().filterByLike(contentLike) { it.content }

    override fun findAllPaged(itemsPerPage: Int, pageNo: Int): Page<WiseSaying> {
        val wiseSayings = findAll()

        val content = wiseSayings
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
