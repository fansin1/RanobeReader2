package org.fansin.ranobereader.ui.novels

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import org.fansin.ranobereader.domain.model.Novel
import javax.inject.Inject

class NovelsViewModel @Inject constructor(
    novelsAdapter: NovelsAdapter,
    val pagedList: LiveData<PagedList<Novel>>
) : ViewModel() {

    private val _novelsAdapter = MutableLiveData<NovelsAdapter>().apply {
        value = novelsAdapter
    }

    val novelsAdapter: LiveData<NovelsAdapter> = _novelsAdapter

    var layoutManagerState = MutableLiveData<Parcelable>()

    fun refresh() {
        pagedList.value!!.dataSource.invalidate()
    }
}
