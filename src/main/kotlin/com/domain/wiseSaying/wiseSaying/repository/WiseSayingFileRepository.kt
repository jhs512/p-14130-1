package com.domain.wiseSaying.wiseSaying.repository

import com.domain.wiseSaying.wiseSaying.entity.WiseSaying
import com.global.app.AppConfig
import java.nio.file.Path

class WiseSayingFileRepository : WiseSayingRepository {
    val tableDirPath: Path
        get() = AppConfig.dbDirPath.resolve("wiseSaying")

    override fun save(wiseSaying: WiseSaying): WiseSaying {
        if (wiseSaying.isNew()) wiseSaying.id = loadLastIdAndIncrease()

        saveOnDisk(wiseSaying)

        return wiseSaying
    }

    override fun isEmpty(): Boolean =
        tableDirPath.toFile()
            .listFiles()
            ?.none { it.name.endsWith(".json") }
            ?: true

    override fun findAll(): List<WiseSaying> =
        tableDirPath.toFile()
            .listFiles()
            ?.filter { it.name.endsWith(".json") }
            ?.map { it.readText() }
            ?.map(WiseSaying.Companion::fromJsonStr)
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

    private fun loadLastIdAndIncrease(): Int {
        val lastId = loadLastId()
        saveLastId(lastId + 1)
        return lastId
    }
}
