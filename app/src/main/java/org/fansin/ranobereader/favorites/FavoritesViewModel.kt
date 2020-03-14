package org.fansin.ranobereader.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.fansin.ranobereader.domain.model.Novel
import org.fansin.ranobereader.domain.repository.FavoritesRepository
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    private val _novels = MutableLiveData<List<Novel>>()

    val text: LiveData<String> = _text
    val novels: LiveData<List<Novel>> = _novels
}
