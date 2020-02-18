package org.fansin.ranobereader.novels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.fansin.ranobereader.domain.RanobeApi
import org.fansin.ranobereader.domain.model.Novel
import org.fansin.ranobereader.domain.model.ResultList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class NovelsViewModel @Inject constructor(
    novelsAdapter: NovelsAdapter,
    private val novelsDataSource: NovelsDataSource
) : ViewModel() {

    private val _failedToLoad = MutableLiveData<Boolean>().apply {
        value = false
    }

    private val _novelsAdapter = MutableLiveData<NovelsAdapter>().apply {
        value = novelsAdapter
    }

    val failedToLoad: LiveData<Boolean> = _failedToLoad
    val novelsAdapter: LiveData<NovelsAdapter> = _novelsAdapter

    fun refresh() {
        novelsDataSource.invalidate()
    }
}
