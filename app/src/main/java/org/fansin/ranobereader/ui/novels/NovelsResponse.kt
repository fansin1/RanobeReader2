package org.fansin.ranobereader.ui.novels

import org.fansin.ranobereader.domain.repository.NovelsRepository

data class NovelsResponse(
    val page: Int,
    val count: Int,
    val novelCallback: NovelsRepository.NovelCallback
)
