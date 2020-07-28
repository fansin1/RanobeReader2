package org.fansin.ranobereader.ui.novels

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.novel_card.view.*
import org.fansin.ranobereader.domain.model.Novel

class NovelViewHolder(
    itemView: View,
    private val novelClicksBinder: NovelClicksBinder
) : RecyclerView.ViewHolder(itemView) {

    var onReadClickListener = { _: View -> }
    private lateinit var novel: Novel

    fun bind(bindNovel: Novel) {
        this.novel = bindNovel
        bindClickListeners()
    }

    private fun bindClickListeners() {
        novelClicksBinder.bind(
            novel,
            itemView.likes,
            itemView.dislikes,
            itemView.favorite
        )
        itemView.book.setOnClickListener(onReadClickListener)
    }
}
