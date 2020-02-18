package org.fansin.ranobereader.di.ui

import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import org.fansin.ranobereader.favorites.FavoritesFragment

@Module(subcomponents = [FavoritesFragmentSubcomponent::class])
abstract class FavoritesFragmentModule {

    @Binds
    @IntoMap
    @ClassKey(FavoritesFragment::class)
    abstract fun bindFavoritesFragmentInjectorFactory(
        factory: FavoritesFragmentSubcomponent.Factory
    ): AndroidInjector.Factory<*>

}