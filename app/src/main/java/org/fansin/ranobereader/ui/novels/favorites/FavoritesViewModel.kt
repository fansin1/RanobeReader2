package org.fansin.ranobereader.ui.novels.favorites

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    favoritesAdapter: FavoritesAdapter
) : ViewModel() {
    private val mutableAdapter = MutableLiveData<FavoritesAdapter>().apply {
        value = favoritesAdapter
    }

    var layoutManagerState = MutableLiveData<Parcelable>()

    val adapter: LiveData<FavoritesAdapter> = mutableAdapter
}
