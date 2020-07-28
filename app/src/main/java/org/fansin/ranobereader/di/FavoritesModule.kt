package org.fansin.ranobereader.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import org.fansin.ranobereader.NovelBinder
import org.fansin.ranobereader.database.FavoritesDao
import org.fansin.ranobereader.database.FavoritesDatabase
import org.fansin.ranobereader.domain.repository.FavoritesRepository
import org.fansin.ranobereader.ui.novels.NovelClicksBinder
import org.fansin.ranobereader.ui.novels.favorites.FavoriteButtonImagesProvider
import org.fansin.ranobereader.ui.novels.favorites.FavoritesAdapter
import javax.inject.Singleton

@Module
class FavoritesModule {

    @Singleton
    @Provides
    fun provideFavoritesDatabase(context: Context): FavoritesDatabase {
        return Room.databaseBuilder(
            context,
            FavoritesDatabase::class.java, "favorites_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideFavoritesDao(favoritesDatabase: FavoritesDatabase): FavoritesDao {
        return favoritesDatabase.favoritesDao()
    }

    @Singleton
    @Provides
    fun provideFavoritesRepository(favoritesDao: FavoritesDao): FavoritesRepository {
        return FavoritesRepository(favoritesDao)
    }

    @Singleton
    @Provides
    fun provideFavoritesAdapter(
        novelBinder: NovelBinder,
        novelClicksBinder: NovelClicksBinder,
        favoritesRepository: FavoritesRepository
    ): FavoritesAdapter {
        return FavoritesAdapter(novelBinder, novelClicksBinder, favoritesRepository.getAll())
    }

    @Singleton
    @Provides
    fun provideFavoriteButtonImagesProvider(context: Context): FavoriteButtonImagesProvider {
        return FavoriteButtonImagesProvider(context)
    }
}
