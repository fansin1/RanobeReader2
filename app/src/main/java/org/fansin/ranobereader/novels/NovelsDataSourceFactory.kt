package org.fansin.ranobereader.novels

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import org.fansin.ranobereader.domain.repository.NovelsRepository

class NovelsDataSourceFactory(
    private val novelsRepository: NovelsRepository,
    private val mutableNovelsNetworkState: MutableLiveData<NovelsNetworkState>
) : DataSource.Factory<Int, NovelRecyclerWrapper>() {
    override fun create(): DataSource<Int, NovelRecyclerWrapper> {
        return NovelsDataSource(novelsRepository, mutableNovelsNetworkState)
    }
}