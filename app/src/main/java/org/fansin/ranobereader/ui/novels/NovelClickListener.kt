package org.fansin.ranobereader.ui.novels

import org.fansin.ranobereader.domain.model.Novel

interface NovelClickListener {
    fun onToBookClick(novel: Novel)

    fun onLikeClick()

    fun onDislikeClick()
}
