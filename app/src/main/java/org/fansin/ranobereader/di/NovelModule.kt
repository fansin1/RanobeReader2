package org.fansin.ranobereader.di

import android.os.Handler
import android.os.Looper
import androidx.paging.PagedList
import dagger.Module
import dagger.Provides
import org.fansin.ranobereader.domain.RanobeApi
import org.fansin.ranobereader.domain.repository.NovelsRepository
import org.fansin.ranobereader.novels.NovelRecyclerWrapper
import org.fansin.ranobereader.novels.NovelsAdapter
import org.fansin.ranobereader.novels.NovelsDataSource
import org.fansin.ranobereader.novels.NovelsDiffUtilCallback
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Module
class NovelModule {

    @Provides
    fun provideNovelsAdapter(diffUtilCallback: NovelsDiffUtilCallback,
                             pagedList: PagedList<NovelRecyclerWrapper>): NovelsAdapter {
        return NovelsAdapter(diffUtilCallback).apply {
            submitList(pagedList)
        }
    }

    @Provides
    fun provideNovelsDataSource(novelsRepository: NovelsRepository): NovelsDataSource {
        return NovelsDataSource(novelsRepository)
    }

    @Provides
    fun providePagedListConfig(): PagedList.Config {
        return PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(16)
            .build()
    }

    @Provides
    fun providePagedList(novelsDataSource: NovelsDataSource,
                         config: PagedList.Config): PagedList<NovelRecyclerWrapper> {
        return PagedList.Builder(novelsDataSource, config)
            .setFetchExecutor(Executors.newFixedThreadPool(5))
            .setNotifyExecutor(object : Executor {

                private val handler = Handler(Looper.getMainLooper())

                override fun execute(command: Runnable) {
                    handler.post(command)
                }
            }).build()
    }

    @Provides
    fun provideNovelsDiffUtilCallback(): NovelsDiffUtilCallback {
        return NovelsDiffUtilCallback()
    }

    @Provides
    fun provideNovelRepository(ranobeApi: RanobeApi): NovelsRepository {
        return NovelsRepository(ranobeApi)
    }

}