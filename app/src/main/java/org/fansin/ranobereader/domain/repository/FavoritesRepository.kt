package org.fansin.ranobereader.domain.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.fansin.ranobereader.database.FavoritesDao
import org.fansin.ranobereader.domain.model.Novel

class FavoritesRepository(
    private val favoritesDao: FavoritesDao
) {

    fun getById(id: Int): Novel {
        return favoritesDao.getById(id)
    }

    fun getAll(): LiveData<List<Novel>> {
        return favoritesDao.getAll()
    }

    fun add(novel: Novel) {
        GlobalScope.launch(Dispatchers.IO) {
            favoritesDao.insert(novel)
        }
    }

    fun remove(novel: Novel) {
        GlobalScope.launch(Dispatchers.IO) {
            favoritesDao.delete(novel)
        }
    }
}
