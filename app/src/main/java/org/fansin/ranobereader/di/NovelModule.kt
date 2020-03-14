package org.fansin.ranobereader.di

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import dagger.Module
import dagger.Provides
import org.fansin.ranobereader.NovelBinder
import org.fansin.ranobereader.domain.model.Novel
import org.fansin.ranobereader.domain.repository.NovelsRepository
import org.fansin.ranobereader.network.ConnectionLiveData
import org.fansin.ranobereader.network.RanobeApi
import org.fansin.ranobereader.novels.NovelsAdapter
import org.fansin.ranobereader.novels.NovelsDataSourceFactory
import org.fansin.ranobereader.novels.NovelsDiffUtilCallback
import org.fansin.ranobereader.novels.NovelsLoadingState
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class NovelModule {

    @Singleton
    @Provides
    fun provideNovelsAdapter(
        novelBinder: NovelBinder,
        diffUtilCallback: NovelsDiffUtilCallback,
        connectionLiveData: ConnectionLiveData
    ): NovelsAdapter {
        return NovelsAdapter(novelBinder, diffUtilCallback, connectionLiveData)
    }

    @Singleton
    @Provides
    fun provideNovelsDataSourceFactory(
        novelsRepository: NovelsRepository,
        mutableNovelsLoadingState: MutableLiveData<NovelsLoadingState>,
        connectionLiveData: ConnectionLiveData
    ): NovelsDataSourceFactory {
        return NovelsDataSourceFactory(
            novelsRepository,
            mutableNovelsLoadingState,
            connectionLiveData
        )
    }

    @Singleton
    @Provides
    fun providePagedListConfig(): PagedList.Config {
        return PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(16)
            .build()
    }

    @Singleton
    @Provides
    fun provideLivePagedList(
        novelsDataSourceFactory: NovelsDataSourceFactory,
        config: PagedList.Config
    ): LiveData<PagedList<Novel>> {
        return LivePagedListBuilder(novelsDataSourceFactory, config)
            .setFetchExecutor(Executors.newFixedThreadPool(5)).build()
    }

    @Singleton
    @Provides
    fun provideNovelsDiffUtilCallback(): NovelsDiffUtilCallback {
        return NovelsDiffUtilCallback()
    }

    @Singleton
    @Provides
    fun provideNovelRepository(ranobeApi: RanobeApi): NovelsRepository {
        return NovelsRepository(ranobeApi)
    }

    @Singleton
    @Provides
    fun provideNovelMutableNetworkState(): MutableLiveData<NovelsLoadingState> {
        return MutableLiveData(NovelsLoadingState.LOADING)
    }

    @Singleton
    @Provides
    fun provideNovelNetworkState(
        novelsLoadingState: MutableLiveData<NovelsLoadingState>
    ): LiveData<NovelsLoadingState> {
        return novelsLoadingState
    }
}
