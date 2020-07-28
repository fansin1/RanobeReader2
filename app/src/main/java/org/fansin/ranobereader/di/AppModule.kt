package org.fansin.ranobereader.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule(@get:Provides val applicationContext: Context)
