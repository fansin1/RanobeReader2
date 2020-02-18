package org.fansin.ranobereader.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import org.fansin.ranobereader.App
import org.fansin.ranobereader.di.ui.MainActivityModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    MainActivityModule::class,
    NetModule::class,
    ViewModelModule::class,
    NovelModule::class
])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun inject(app: App)
}