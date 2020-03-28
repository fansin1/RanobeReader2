package org.fansin.ranobereader.ui.novels.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.fansin.ranobereader.domain.model.Novel
import javax.inject.Inject

class FavoritesViewModel @Inject constructor() : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    private val _novels = MutableLiveData<List<Novel>>()

    val text: LiveData<String> = _text
    val novels: LiveData<List<Novel>> = _novels
}
