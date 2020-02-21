package org.fansin.ranobereader.novels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.fansin.ranobereader.ApplicationConfig
import org.fansin.ranobereader.domain.model.Novel
import org.fansin.ranobereader.domain.repository.NovelsRepository

class NovelsDataSource(
    private val novelsRepository: NovelsRepository,
    private val mutableNovelsNetworkState: MutableLiveData<NovelsNetworkState>
) : PageKeyedDataSource<Int, NovelRecyclerWrapper>() {

    private var retries = 0

    override fun loadInitial(params: LoadInitialParams<Int>,
                             callback: LoadInitialCallback<Int, NovelRecyclerWrapper>) {
        mutableNovelsNetworkState.postValue(NovelsNetworkState.LOADING)
        novelsRepository.loadData(1, params.requestedLoadSize,
            object : NovelsRepository.NovelCallback {
                override fun onResult(data: List<Novel>) {
                    callback.onResult(data.map { NovelRecyclerWrapper(it) },
                        0, 1 + params.requestedLoadSize / 16)
                    mutableNovelsNetworkState.postValue(NovelsNetworkState.LOADED_INIT)
                }

                override fun onError(t: Throwable) {
                    retry(NovelsNetworkState.ERROR_INIT) {
                        loadInitial(params, callback)
                    }
                }
            })
    }

    override fun loadAfter(params: LoadParams<Int>,
                           callback: LoadCallback<Int, NovelRecyclerWrapper>) {
        novelsRepository.loadData(params.key, params.requestedLoadSize,
            object : NovelsRepository.NovelCallback {
                override fun onResult(data: List<Novel>) {
                    callback.onResult(data.map { NovelRecyclerWrapper(it) },
                        params.key + 1)
                    mutableNovelsNetworkState.postValue(NovelsNetworkState.LOADED_AFTER)
                }

                override fun onError(t: Throwable) {
                    retry(NovelsNetworkState.ERROR_AFTER) {
                        loadAfter(params, callback)
                    }
                }
            })
    }

    override fun loadBefore(params: LoadParams<Int>,
                            callback: LoadCallback<Int, NovelRecyclerWrapper>) {
        Log.d("test", "test")
    }

    private fun retry(state: NovelsNetworkState, action: () -> Unit) {
        retries++
        if (retries < ApplicationConfig.NOVEL_LOAD_RETRIES) {
            GlobalScope.launch {
                delay(ApplicationConfig.NOVEL_RETRY_DELAY)
                action()
            }
        } else {
            retries = 0
            mutableNovelsNetworkState.postValue(state)
        }
    }
}