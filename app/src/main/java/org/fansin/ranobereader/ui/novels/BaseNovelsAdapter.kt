package org.fansin.ranobereader.ui.novels

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import org.fansin.ranobereader.NovelBinder
import org.fansin.ranobereader.R
import org.fansin.ranobereader.domain.model.Novel

open class BaseNovelsAdapter(
    private val novelBinder: NovelBinder,
    diffUtilCallback: DiffUtil.ItemCallback<Novel>
) : PagedListAdapter<Novel, NovelViewHolder>(diffUtilCallback) {

    private lateinit var novelClickListener: NovelClickListener

    fun setOnClickListener(novelClickListener: NovelClickListener) {
        this.novelClickListener = novelClickListener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NovelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.novel_card, parent, false)

        return NovelViewHolder(view)
    }

    override fun onBindViewHolder(holder: NovelViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { novel ->
            holder.bind(novel)
            holder.setNovelClickListener(novelClickListener)
            novelBinder.bind(holder.itemView, novel)
        }
    }
}
