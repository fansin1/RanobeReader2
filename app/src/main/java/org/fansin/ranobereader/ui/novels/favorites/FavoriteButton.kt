package org.fansin.ranobereader.ui.novels.favorites

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton
import org.fansin.ranobereader.App

class FavoriteButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialButton(context, attrs, defStyleAttr) {

    private val imagesProvider = App.appComponent.getFavoriteButtonImagesProvider()

    var isFavorite: Boolean = false
        set(value) {
            icon = if (value) {
                imagesProvider.active
            } else {
                imagesProvider.inactive
            }
            field = value
        }
}
