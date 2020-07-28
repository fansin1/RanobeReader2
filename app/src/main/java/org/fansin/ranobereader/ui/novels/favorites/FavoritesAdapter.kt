package org.fansin.ranobereader.ui.novels.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import org.fansin.ranobereader.NovelBinder
import org.fansin.ranobereader.R
import org.fansin.ranobereader.domain.model.Novel
import org.fansin.ranobereader.ui.novels.NovelClicksBinder
import org.fansin.ranobereader.ui.novels.NovelViewHolder

class FavoritesAdapter(
    private val novelBinder: NovelBinder,
    private val novelClicksBinder: NovelClicksBinder,
    private val items: LiveData<List<Novel>>
) : RecyclerView.Adapter<NovelViewHolder>() {

    var onReadClickListener = { _: View -> }
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        items.observeForever {
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = items.value?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NovelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.novel_card, parent, false)

        return NovelViewHolder(view, novelClicksBinder)
    }

    override fun onBindViewHolder(holder: NovelViewHolder, position: Int) {
        val item = items.value?.get(position)
        item?.let { novel ->
            holder.bind(novel)
            holder.onReadClickListener = onReadClickListener
            novelBinder.bind(holder.itemView, novel)
        }
    }
}
