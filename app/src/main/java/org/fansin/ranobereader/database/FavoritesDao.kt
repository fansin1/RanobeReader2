package org.fansin.ranobereader.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import org.fansin.ranobereader.domain.model.Novel

@Dao
interface FavoritesDao {
    @Query("SELECT * from novel")
    fun getAll(): List<Novel>

    @Insert
    fun insert(novel: Novel)

    @Delete
    fun delete(novel: Novel)

    @Update
    fun update(novel: Novel)
}
