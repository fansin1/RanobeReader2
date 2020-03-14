package org.fansin.ranobereader.novels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
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

    private var retries = 0
    private var lastFailedRetry: (() -> Unit)? = null
    private var connectionRestored = false

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
        novelsRepository.loadData(1, params.requestedLoadSize,
            object : NovelsRepository.NovelCallback {
                override fun onResult(data: List<Novel>) {
                    callback.onResult(
                        data.map { it },
                        0, 1 + params.requestedLoadSize / 16
                    )
                    mutableNovelsLoadingState.postValue(NovelsLoadingState.LOADED_INIT)
                }

                override fun onError(t: Throwable) {
                    retry(NovelsLoadingState.ERROR_INIT) {
                        loadInitial(params, callback)
                    }
                }
            })
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Novel>
    ) {
        novelsRepository.loadData(params.key, params.requestedLoadSize,
            object : NovelsRepository.NovelCallback {
                override fun onResult(data: List<Novel>) {
                    callback.onResult(
                        data.map { it },
                        params.key + 1
                    )
                    mutableNovelsLoadingState.postValue(NovelsLoadingState.LOADED_AFTER)
                }

                override fun onError(t: Throwable) {
                    retry(NovelsLoadingState.ERROR_AFTER) {
                        loadAfter(params, callback)
                    }
                }
            })
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Novel>
    ) {
        Log.d("test", "test")
    }

    private fun onConnectionRestored() {
        if (lastFailedRetry == null) {
            connectionRestored = true
        } else {
            lastFailedRetry?.invoke()
        }
    }

    private fun retry(state: NovelsLoadingState, loadAction: () -> Unit) {
        retries++
        if (retries < ApplicationConfig.NOVEL_LOAD_RETRIES) {
            GlobalScope.launch {
                delay(ApplicationConfig.NOVEL_RETRY_DELAY)
                loadAction()
            }
        } else {
            if (connectionRestored) {
                loadAction()
            } else {
                lastFailedRetry = loadAction
            }
            retries = 0
            mutableNovelsLoadingState.postValue(state)
        }
        connectionRestored = false
    }
}
