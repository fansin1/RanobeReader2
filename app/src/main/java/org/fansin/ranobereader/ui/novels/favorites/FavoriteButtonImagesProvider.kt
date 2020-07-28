package org.fansin.ranobereader.ui.novels.favorites

import android.content.Context
import androidx.core.content.ContextCompat
import org.fansin.ranobereader.R

class FavoriteButtonImagesProvider(context: Context) {
    val active = ContextCompat.getDrawable(context, R.drawable.ic_favorite_active)!!
    val inactive = ContextCompat.getDrawable(context, R.drawable.ic_favorite_inactive)!!
}
