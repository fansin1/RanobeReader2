package org.fansin.ranobereader.novels

import android.os.Build
import android.text.Html
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.novel_card.view.*

class NovelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(wrapper: NovelRecyclerWrapper) {
        val novel = wrapper.novel
        itemView.title.text = novel.title
        if (novel.imageUrl.isNotEmpty()) {
            Picasso.get().load(novel.imageUrl).into(itemView.image)
        }
        if (novel.author.isNotEmpty()) {
            itemView.author.apply {
                visibility = View.VISIBLE
                text = "Author: ${novel.author}"
            }
        } else {
            itemView.author.visibility = View.GONE
        }
        if (novel.description.isNotEmpty()) {
            itemView.description.apply {
                visibility = View.VISIBLE
                text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(novel.description, Html.FROM_HTML_MODE_COMPACT)
                } else {
                    Html.fromHtml(novel.description)
                }
            }
        } else {
            itemView.description.visibility = View.GONE
        }

        updateState(wrapper)

        itemView.setOnClickListener {
            wrapper.isExpanded = !wrapper.isExpanded
            updateState(wrapper)
        }
    }

    private fun updateState(wrapper: NovelRecyclerWrapper) {
        if (wrapper.isExpanded) {
            itemView.description.maxLines = Int.MAX_VALUE
        } else {
            itemView.description.maxLines = 4
        }
    }
}
