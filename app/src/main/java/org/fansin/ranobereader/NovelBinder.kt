package org.fansin.ranobereader

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.fansin.ranobereader.domain.model.Novel
import org.fansin.ranobereader.domain.repository.FavoritesRepository
import org.fansin.ranobereader.ui.novels.favorites.FavoriteButton
import kotlin.reflect.KProperty1

class NovelBinder(private val favoritesRepository: FavoritesRepository) {

    private fun <T, K> KProperty1<T, K>.lambda() = { propertyOwner: T ->
        this.get(propertyOwner).toString()
    }

    private val binds = mapOf(
        R.id.likes to Novel::likesCount.lambda(),
        R.id.dislikes to Novel::dislikesCount.lambda(),
        R.id.favorite to { _ -> "" },
        R.id.author to { novel: Novel -> novel.author.name },
        R.id.title to Novel::title.lambda(),
        R.id.description to Novel::description.lambda(),
        R.id.image to { novel: Novel ->
            if (novel.images.images.isNotEmpty()) {
                novel.images.images[2].url
            } else {
                ""
            }
        }
    )

    fun bind(itemView: View, novel: Novel) {
        for (i in binds) {
            when (val view = itemView.findViewById<View>(i.key)) {
                is FavoriteButton -> bindFavoriteButton(view, novel)
                is TextView -> bindTextView(view, i.value(novel))
                is ImageView -> bindImageView(view, i.value(novel))
                else -> {
                    // do nothing
                }
            }
        }
    }

    private fun bindFavoriteButton(itemView: FavoriteButton, novel: Novel) {
        GlobalScope.launch(Dispatchers.Main) {
            val isFavorite = GlobalScope.async(Dispatchers.IO) {
                favoritesRepository.has(novel)
            }
            itemView.isFavorite = isFavorite.await()
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
