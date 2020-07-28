package org.fansin.ranobereader.ui.novels

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import org.fansin.ranobereader.domain.model.Novel
import javax.inject.Inject

class NovelsViewModel @Inject constructor(
    private val pagedList: LiveData<PagedList<Novel>>
) : ViewModel() {

    var layoutManagerState = MutableLiveData<Parcelable>()

    fun refresh() {
        pagedList.value?.dataSource?.invalidate()
    }
}
