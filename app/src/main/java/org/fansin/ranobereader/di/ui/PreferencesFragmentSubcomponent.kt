package org.fansin.ranobereader.di.ui

import dagger.Subcomponent
import dagger.android.AndroidInjector
import org.fansin.ranobereader.preferences.PreferencesFragment

@Subcomponent
interface PreferencesFragmentSubcomponent : AndroidInjector<PreferencesFragment> {
    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<PreferencesFragment>
}