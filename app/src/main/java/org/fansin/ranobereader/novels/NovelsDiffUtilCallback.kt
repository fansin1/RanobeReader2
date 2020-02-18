package org.fansin.ranobereader.novels

import androidx.recyclerview.widget.DiffUtil

class NovelsDiffUtilCallback : DiffUtil.ItemCallback<NovelRecyclerWrapper>() {
    override fun areItemsTheSame(oldItem: NovelRecyclerWrapper,
                                 newItem: NovelRecyclerWrapper): Boolean {
        return oldItem.novel.id == newItem.novel.id
    }

    override fun areContentsTheSame(oldItem: NovelRecyclerWrapper,
                                    newItem: NovelRecyclerWrapper): Boolean {
        return oldItem.novel.id == newItem.novel.id
    }
}