package org.fansin.ranobereader.ui.novels

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import org.fansin.ranobereader.NovelBinder
import org.fansin.ranobereader.R
import org.fansin.ranobereader.domain.model.Novel

class NovelsAdapter(
    private val novelBinder: NovelBinder,
    private val novelClicksBinder: NovelClicksBinder,
    diffUtilCallback: DiffUtil.ItemCallback<Novel>,
    pagedList: LiveData<PagedList<Novel>>
) : PagedListAdapter<Novel, NovelViewHolder>(diffUtilCallback) {

    var onReadClickListener = { _: Novel -> }

    init {
        pagedList.observeForever { list ->
            submitList(list)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NovelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.novel_card, parent, false)

        return NovelViewHolder(view, novelClicksBinder)
    }

    override fun onBindViewHolder(holder: NovelViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { novel ->
            holder.bind(novel)
            holder.onReadClickListener = { onReadClickListener(novel) }
            novelBinder.bind(holder.itemView, novel)
        }
    }
}
