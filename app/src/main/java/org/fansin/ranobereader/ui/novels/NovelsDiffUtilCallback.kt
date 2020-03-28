package org.fansin.ranobereader.ui.novels

import androidx.recyclerview.widget.DiffUtil
import org.fansin.ranobereader.domain.model.Novel

class NovelsDiffUtilCallback : DiffUtil.ItemCallback<Novel>() {
    override fun areItemsTheSame(
        oldItem: Novel,
        newItem: Novel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Novel,
        newItem: Novel
    ): Boolean {
        return oldItem.id == newItem.id
    }
}
