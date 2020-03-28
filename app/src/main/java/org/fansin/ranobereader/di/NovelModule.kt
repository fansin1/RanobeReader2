package org.fansin.ranobereader.di

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import dagger.Module
import dagger.Provides
import org.fansin.ranobereader.ApplicationConfig
import org.fansin.ranobereader.NovelBinder
import org.fansin.ranobereader.domain.model.Novel
import org.fansin.ranobereader.domain.repository.NovelsRepository
import org.fansin.ranobereader.network.ConnectionLiveData
import org.fansin.ranobereader.network.RanobeApi
import org.fansin.ranobereader.ui.novels.NovelsAdapter
import org.fansin.ranobereader.ui.novels.NovelsDataSourceFactory
import org.fansin.ranobereader.ui.novels.NovelsDiffUtilCallback
import org.fansin.ranobereader.ui.novels.NovelsLoadingState
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class NovelModule {

    @Singleton
    @Provides
    fun provideNovelsAdapter(
        novelBinder: NovelBinder,
        diffUtilCallback: NovelsDiffUtilCallback,
        livePagedList: LiveData<PagedList<Novel>>
    ): NovelsAdapter {
        return NovelsAdapter(novelBinder, diffUtilCallback, livePagedList)
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
            .setPageSize(ApplicationConfig.NOVELS_PER_PAGE)
            .build()
    }

    @Singleton
    @Provides
    fun provideLivePagedList(
        novelsDataSourceFactory: NovelsDataSourceFactory,
        config: PagedList.Config
    ): LiveData<PagedList<Novel>> {
        return LivePagedListBuilder(novelsDataSourceFactory, config)
            .setFetchExecutor(
                Executors
                    .newFixedThreadPool(ApplicationConfig.PAGED_LIST_EXECUTORS_COUNT)
            ).build()
    }

    @Singleton
    @Provides
    fun provideNovelsDiffUtilCallback(): NovelsDiffUtilCallback {
        return NovelsDiffUtilCallback()
    }

    @Singleton
    @Provides
    fun provideNovelRepository(
        ranobeApi: RanobeApi
    ): NovelsRepository {
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
