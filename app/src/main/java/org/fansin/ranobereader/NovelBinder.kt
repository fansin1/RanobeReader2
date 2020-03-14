package org.fansin.ranobereader

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import org.fansin.ranobereader.domain.model.Novel
import kotlin.reflect.KProperty1

class NovelBinder {

    private fun <T, K> KProperty1<T, K>.lambda() = { propertyOwner: T ->
        this.get(propertyOwner).toString()
    }

    private val binds = mapOf(
        R.id.likes to Novel::likesCount.lambda(),
        R.id.dislikes to Novel::dislikesCount.lambda(),
        R.id.author to { novel: Novel -> novel.author.name },
        R.id.title to Novel::title.lambda(),
        R.id.description to Novel::description.lambda(),
        R.id.image to { novel: Novel ->
            if (novel.images.isNotEmpty()) {
                novel.images.first().url
            } else {
                ""
            }
        }
    )

    fun bind(itemView: View, novel: Novel) {
        for (i in binds) {
            when (val view = itemView.findViewById<View>(i.key)) {
                is TextView -> bindTextView(view, i.value(novel))
                is ImageView -> bindImageView(view, i.value(novel))
                else -> {
                    // do nothing
                }
            }
        }
    }

    private fun bindTextView(view: TextView, newText: String) {
        if (newText.isNotEmpty()) {
            view.apply {
                visibility = View.VISIBLE
                text = fromHtml(newText)
            }
        } else {
            view.visibility = View.GONE
        }
    }

    private fun bindImageView(view: ImageView, url: String) {
        if (url.isNotEmpty()) {
            Picasso.get()
                .load(url)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(view)
        }
    }

    private fun fromHtml(text: String) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(text)
        }
}
