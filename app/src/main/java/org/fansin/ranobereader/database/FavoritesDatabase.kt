package org.fansin.ranobereader.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.fansin.ranobereader.domain.model.Novel

@Database(entities = [Novel::class], version = 1)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}
