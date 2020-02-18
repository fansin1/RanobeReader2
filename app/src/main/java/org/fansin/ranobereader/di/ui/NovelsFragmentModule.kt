package org.fansin.ranobereader.di.ui

import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import org.fansin.ranobereader.novels.NovelsFragment

@Module(subcomponents = [NovelsFragmentSubcomponent::class])
abstract class NovelsFragmentModule {

    @Binds
    @IntoMap
    @ClassKey(NovelsFragment::class)
    abstract fun bindNovelsFragmentInjectorFactory(
        factory: NovelsFragmentSubcomponent.Factory
    ): AndroidInjector.Factory<*>

}