package org.fansin.ranobereader.ui.novels

import android.widget.Button
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.fansin.ranobereader.domain.model.Novel
import org.fansin.ranobereader.domain.repository.FavoritesRepository
import org.fansin.ranobereader.ui.novels.favorites.FavoriteButton

class NovelClicksBinder(
    private val favoritesRepository: FavoritesRepository
) {

    fun bind(
        novel: Novel,
        likes: Button,
        dislikes: Button,
        favorite: FavoriteButton
    ) {
        likes.setOnClickListener { onLikeClick(novel) }
        dislikes.setOnClickListener { onDislikeClick(novel) }
        favorite.setOnClickListener {
            onFavoriteClick(novel) { added ->
                favorite.isFavorite = added
            }
        }
    }

    private fun onLikeClick(novel: Novel) {
        //
    }

    private fun onDislikeClick(novel: Novel) {
        //
    }

    private fun onFavoriteClick(novel: Novel, addedCallback: (Boolean) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            if (favoritesRepository.has(novel)) {
                favoritesRepository.remove(novel)
                callOnUiThread(false, addedCallback)
            } else {
                favoritesRepository.add(novel)
                callOnUiThread(true, addedCallback)
            }
        }
    }

    private fun callOnUiThread(value: Boolean, addedCallback: (Boolean) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            addedCallback(value)
        }
    }
}
