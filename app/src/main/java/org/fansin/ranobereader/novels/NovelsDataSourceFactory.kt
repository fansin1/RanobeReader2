package org.fansin.ranobereader.novels

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import org.fansin.ranobereader.domain.model.Novel
import org.fansin.ranobereader.domain.repository.NovelsRepository
import org.fansin.ranobereader.network.ConnectionLiveData

class NovelsDataSourceFactory(
    private val novelsRepository: NovelsRepository,
    private val mutableNovelsLoadingState: MutableLiveData<NovelsLoadingState>,
    private val connectionLiveData: ConnectionLiveData
) : DataSource.Factory<Int, Novel>() {
    override fun create(): DataSource<Int, Novel> {
        return NovelsDataSource(novelsRepository, mutableNovelsLoadingState, connectionLiveData)
    }
}
