package org.fansin.ranobereader.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.fansin.ranobereader.favorites.FavoritesViewModel
import org.fansin.ranobereader.novels.NovelsViewModel
import org.fansin.ranobereader.preferences.PreferencesViewModel
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Singleton
class ViewModelFactory @Inject constructor(
    private val viewModels: MutableMap<Class<out ViewModel>,
    Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T
            = viewModels[modelClass]?.get() as T
}

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(NovelsViewModel::class)
    internal abstract fun bindNovelsViewModel(viewModel: NovelsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel::class)
    internal abstract fun bindFavoritesViewModel(viewModel: FavoritesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PreferencesViewModel::class)
    internal abstract fun bindPreferenceViewModel(viewModel: PreferencesViewModel): ViewModel

}