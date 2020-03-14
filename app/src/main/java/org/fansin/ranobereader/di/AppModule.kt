package org.fansin.ranobereader.di

import android.content.Context
import dagger.Module
import dagger.Provides
import org.fansin.ranobereader.NovelBinder

@Module
class AppModule(@get:Provides val applicationContext: Context) {

    @Provides
    fun provideNovelBinder(): NovelBinder {
        return NovelBinder()
    }
}
