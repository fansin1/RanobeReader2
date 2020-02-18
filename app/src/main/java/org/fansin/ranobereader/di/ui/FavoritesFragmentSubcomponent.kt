package org.fansin.ranobereader.di.ui

import dagger.Subcomponent
import dagger.android.AndroidInjector
import org.fansin.ranobereader.favorites.FavoritesFragment

@Subcomponent
interface FavoritesFragmentSubcomponent : AndroidInjector<FavoritesFragment> {
    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<FavoritesFragment>
}