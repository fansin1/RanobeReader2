package org.fansin.ranobereader.novels

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.fansin.ranobereader.NovelBinder
import org.fansin.ranobereader.R
import org.fansin.ranobereader.domain.model.Novel
import org.fansin.ranobereader.network.ConnectionLiveData

class NovelsAdapter(
    private val novelBinder: NovelBinder,
    diffUtilCallback: DiffUtil.ItemCallback<Novel>,
    connectionLiveData: ConnectionLiveData
) : PagedListAdapter<Novel, NovelViewHolder>(diffUtilCallback) {

    private lateinit var novelClickListener: NovelClickListener

    interface NovelClickListener {
        fun onToBookClick(novel: Novel)

        fun onLikeClick()

        fun onDislikeClick()
    }

    init {
        initUpdateOnConnectionStateChanged(connectionLiveData)
    }

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

    private fun initUpdateOnConnectionStateChanged(connectionLiveData: ConnectionLiveData) {
        connectionLiveData.observeForever {
            if (it) {
                GlobalScope.launch(Dispatchers.Main) {
                    notifyDataSetChanged()
                }
            }
        }
    }
}
