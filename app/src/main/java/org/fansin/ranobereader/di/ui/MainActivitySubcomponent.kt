package org.fansin.ranobereader.di.ui

import dagger.Subcomponent
import dagger.android.AndroidInjector
import org.fansin.ranobereader.MainActivity
import org.fansin.ranobereader.di.ActivityScope
import org.fansin.ranobereader.di.NetModule
import org.fansin.ranobereader.di.ViewModelModule

@ActivityScope
@Subcomponent(modules = [
    FavoritesFragmentModule::class,
    NovelsFragmentModule::class,
    PreferencesFragmentModule::class])
interface MainActivitySubcomponent : AndroidInjector<MainActivity> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<MainActivity>

}