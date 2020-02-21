package org.fansin.ranobereader.novels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import javax.inject.Inject

class NovelsViewModel @Inject constructor(
    novelsAdapter: NovelsAdapter,
    val pagedList: LiveData<PagedList<NovelRecyclerWrapper>>
) : ViewModel() {

    private val _novelsAdapter = MutableLiveData<NovelsAdapter>().apply {
        value = novelsAdapter
    }

    val novelsAdapter: LiveData<NovelsAdapter> = _novelsAdapter

    fun refresh() {
        pagedList.value!!.dataSource.invalidate()
    }
}
