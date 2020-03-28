package org.fansin.ranobereader.ui.novels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.fansin.ranobereader.ApplicationConfig
import org.fansin.ranobereader.domain.model.Novel
import org.fansin.ranobereader.domain.repository.NovelsRepository
import org.fansin.ranobereader.network.ConnectionLiveData

class NovelsDataSource(
    private val novelsRepository: NovelsRepository,
    private val mutableNovelsLoadingState: MutableLiveData<NovelsLoadingState>,
    connectionLiveData: ConnectionLiveData
) : PageKeyedDataSource<Int, Novel>() {

    private var lastFailedResponse: NovelsResponse? = null

    init {
        GlobalScope.launch(Dispatchers.Main) {
            connectionLiveData.observeForever { connected ->
                if (connected) {
                    onConnectionRestored()
                }
            }
        }
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Novel>
    ) {
        mutableNovelsLoadingState.postValue(NovelsLoadingState.LOADING)
        novelsRepository.loadData(
            NovelsResponse(
                1,
                params.requestedLoadSize,
                createNovelCallback(params, callback)
            )
        )
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Novel>
    ) {
        novelsRepository.loadData(
            NovelsResponse(
                params.key,
                params.requestedLoadSize,
                createNovelCallback(params, callback)
            )
        )
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Novel>
    ) {
        Log.d("test", "test")
    }

    private fun createNovelCallback(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Novel>
    ): NovelsRepository.NovelCallback {
        return object : NovelsRepository.NovelCallback {
            override fun onResult(data: List<Novel>) {
                callback.onResult(
                    data.map { it },
                    0,
                    nextPageNumber(1, params.requestedLoadSize)
                )
                mutableNovelsLoadingState.postValue(NovelsLoadingState.LOADED_INIT)
            }

            override fun onError(t: Throwable) {
                lastFailedResponse = NovelsResponse(
                    1,
                    params.requestedLoadSize,
                    this
                )
                mutableNovelsLoadingState.postValue(NovelsLoadingState.ERROR_INIT)
            }
        }
    }

    private fun createNovelCallback(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Novel>
    ): NovelsRepository.NovelCallback {
        return object : NovelsRepository.NovelCallback {
            override fun onResult(data: List<Novel>) {
                callback.onResult(data.map { it }, params.key + 1)
                mutableNovelsLoadingState.postValue(NovelsLoadingState.LOADED_AFTER)
            }

            override fun onError(t: Throwable) {
                lastFailedResponse = NovelsResponse(
                    params.key,
                    params.requestedLoadSize,
                    this
                )
                mutableNovelsLoadingState.postValue(NovelsLoadingState.ERROR_AFTER)
            }
        }
    }

    private fun onConnectionRestored() {
        lastFailedResponse?.let { response ->
            mutableNovelsLoadingState.postValue(NovelsLoadingState.LOADING)
            novelsRepository.loadData(response)
        }
    }

    private fun nextPageNumber(initPageNumber: Int, loadSize: Int): Int {
        return initPageNumber + loadSize / ApplicationConfig.NOVELS_PER_PAGE
    }
}
