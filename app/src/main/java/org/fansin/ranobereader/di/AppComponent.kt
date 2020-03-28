package org.fansin.ranobereader.di

import dagger.Component
import org.fansin.ranobereader.App
import org.fansin.ranobereader.ui.FavoritesFragment
import org.fansin.ranobereader.ui.MainActivity
import org.fansin.ranobereader.ui.NovelDetailsActivity
import org.fansin.ranobereader.ui.NovelsFragment
import org.fansin.ranobereader.ui.PreferencesFragment
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        NetModule::class,
        ViewModelModule::class,
        NovelModule::class,
        FavoritesModule::class
    ]
)
@Singleton
interface AppComponent {
    fun inject(app: App)

    fun inject(mainActivity: MainActivity)

    fun inject(novelsFragment: NovelsFragment)

    fun inject(favoritesFragment: FavoritesFragment)

    fun inject(preferencesFragment: PreferencesFragment)

    fun inject(novelDetailsActivity: NovelDetailsActivity)
}
