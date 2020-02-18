package org.fansin.ranobereader.novels

import androidx.paging.PageKeyedDataSource
import androidx.paging.PositionalDataSource
import org.fansin.ranobereader.domain.model.Novel
import org.fansin.ranobereader.domain.repository.NovelsRepository

class NovelsDataSource(
    private val novelsRepository: NovelsRepository
) : PageKeyedDataSource<Int, NovelRecyclerWrapper>() {

    override fun loadInitial(params: LoadInitialParams<Int>,
                             callback: LoadInitialCallback<Int, NovelRecyclerWrapper>
    ) {
        novelsRepository.loadData(1, params.requestedLoadSize,
            object : NovelsRepository.NovelCallback {
                override fun onResult(data: List<Novel>) {
                    callback.onResult(data.map { NovelRecyclerWrapper(it) },
                        0, 1 + params.requestedLoadSize / 16)
                }

                override fun onError(t: Throwable) {
                    callback.onError(t)
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
                }

                override fun onError(t: Throwable) {
                    callback.onError(t)
                }
            })
    }

    override fun loadBefore(params: LoadParams<Int>,
                            callback: LoadCallback<Int, NovelRecyclerWrapper>) {
    }

}