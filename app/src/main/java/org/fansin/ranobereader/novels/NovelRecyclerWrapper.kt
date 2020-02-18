package org.fansin.ranobereader.novels

import org.fansin.ranobereader.domain.model.Novel

data class NovelRecyclerWrapper(
    val novel: Novel
) {
    var isExpanded = false
}