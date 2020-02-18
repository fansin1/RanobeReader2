package org.fansin.ranobereader.di.ui

import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import org.fansin.ranobereader.preferences.PreferencesFragment

@Module(subcomponents = [PreferencesFragmentSubcomponent::class])
abstract class PreferencesFragmentModule {

    @Binds
    @IntoMap
    @ClassKey(PreferencesFragment::class)
    abstract fun bindPreferencesFragmentInjectorFactory(
        factory: PreferencesFragmentSubcomponent.Factory
    ): AndroidInjector.Factory<*>

}