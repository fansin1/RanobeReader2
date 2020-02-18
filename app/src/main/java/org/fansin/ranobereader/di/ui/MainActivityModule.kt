package org.fansin.ranobereader.di.ui

import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import org.fansin.ranobereader.MainActivity

@Module(subcomponents = [MainActivitySubcomponent::class])
abstract class MainActivityModule {

    @Binds
    @IntoMap
    @ClassKey(MainActivity::class)
    abstract fun bindMainActivityInjectorFactory(
        factory: MainActivitySubcomponent.Factory
    ): AndroidInjector.Factory<*>

}