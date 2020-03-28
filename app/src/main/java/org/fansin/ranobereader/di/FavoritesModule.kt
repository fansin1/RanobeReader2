package org.fansin.ranobereader.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import org.fansin.ranobereader.database.FavoritesDao
import org.fansin.ranobereader.database.FavoritesDatabase
import org.fansin.ranobereader.domain.repository.FavoritesRepository
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
}
