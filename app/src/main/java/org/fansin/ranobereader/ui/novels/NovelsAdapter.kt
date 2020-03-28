package org.fansin.ranobereader.ui.novels

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import org.fansin.ranobereader.NovelBinder
import org.fansin.ranobereader.domain.model.Novel

class NovelsAdapter(
    novelBinder: NovelBinder,
    diffUtilCallback: DiffUtil.ItemCallback<Novel>,
    pagedList: LiveData<PagedList<Novel>>
) : BaseNovelsAdapter(novelBinder, diffUtilCallback) {

    init {
        pagedList.observeForever { list ->
            submitList(list)
        }
    }
}
