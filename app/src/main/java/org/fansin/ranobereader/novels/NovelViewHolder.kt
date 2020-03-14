package org.fansin.ranobereader.novels

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.novel_card.view.*
import org.fansin.ranobereader.domain.model.Novel
import org.fansin.ranobereader.novels.NovelsAdapter.NovelClickListener

class NovelViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private lateinit var novel: Novel
    private lateinit var novelClickListener: NovelClickListener

    fun setNovelClickListener(novelClickListener: NovelClickListener) {
        this.novelClickListener = novelClickListener
    }

    fun bind(bindNovel: Novel) {
        this.novel = bindNovel
        bindClickListeners()
    }

    private fun bindClickListeners() {
        itemView.book.setOnClickListener { novelClickListener.onToBookClick(novel) }
        itemView.likes.setOnClickListener { novelClickListener.onLikeClick() }
        itemView.dislikes.setOnClickListener { novelClickListener.onDislikeClick() }
    }
}
