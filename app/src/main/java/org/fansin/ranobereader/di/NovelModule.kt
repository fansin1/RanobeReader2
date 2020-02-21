package org.fansin.ranobereader.di

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import dagger.Module
import dagger.Provides
import org.fansin.ranobereader.domain.RanobeApi
import org.fansin.ranobereader.domain.repository.NovelsRepository
import org.fansin.ranobereader.novels.*
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class NovelModule {

    @Singleton
    @Provides
    fun provideNovelsAdapter(diffUtilCallback: NovelsDiffUtilCallback): NovelsAdapter {
        return NovelsAdapter(diffUtilCallback)
    }

    @Singleton
    @Provides
    fun provideNovelsDataSourceFactory(
        novelsRepository: NovelsRepository,
        mutableNovelsNetworkState: MutableLiveData<NovelsNetworkState>
    ): NovelsDataSourceFactory {
        return NovelsDataSourceFactory(novelsRepository, mutableNovelsNetworkState)
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
    fun provideLivePagedList(novelsDataSourceFactory: NovelsDataSourceFactory,
                         config: PagedList.Config): LiveData<PagedList<NovelRecyclerWrapper>> {
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
    fun provideNovelMutableNetworkState(): MutableLiveData<NovelsNetworkState> {
        return MutableLiveData(NovelsNetworkState.LOADING)
    }

    @Singleton
    @Provides
    fun provideNovelNetworkState(novelsNetworkState: MutableLiveData<NovelsNetworkState>
    ): LiveData<NovelsNetworkState> {
        return novelsNetworkState
    }
}