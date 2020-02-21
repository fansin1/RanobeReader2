package org.fansin.ranobereader.di.ui

import dagger.Subcomponent
import dagger.android.AndroidInjector
import org.fansin.ranobereader.novels.NovelsFragment

@Subcomponent
interface NovelsFragmentSubcomponent : AndroidInjector<NovelsFragment> {
    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<NovelsFragment>
}