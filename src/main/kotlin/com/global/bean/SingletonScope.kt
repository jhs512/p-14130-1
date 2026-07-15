package com.global.bean

import com.domain.system.system.controller.SystemController
import com.domain.wiseSaying.wiseSaying.controller.WiseSayingController
import com.domain.wiseSaying.wiseSaying.repository.WiseSayingMemoryRepository
import com.domain.wiseSaying.wiseSaying.repository.WiseSayingRepository
import com.domain.wiseSaying.wiseSaying.service.WiseSayingService

object SingletonScope {
    val wiseSayingController by lazy { WiseSayingController() }
    val wiseSayingService by lazy { WiseSayingService() }
    val wiseSayingRepository: WiseSayingRepository by lazy { WiseSayingMemoryRepository() }
    val systemController by lazy { SystemController() }
}
