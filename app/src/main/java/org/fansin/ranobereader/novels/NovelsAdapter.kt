package org.fansin.ranobereader.novels

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import org.fansin.ranobereader.R
import org.fansin.ranobereader.domain.model.Novel

class NovelsAdapter(
    diffUtilCallback: DiffUtil.ItemCallback<NovelRecyclerWrapper>
) : PagedListAdapter<NovelRecyclerWrapper, NovelViewHolder>(diffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NovelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.novel_card, parent, false)
        return NovelViewHolder(view)
    }

    override fun onBindViewHolder(holder: NovelViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }
}
