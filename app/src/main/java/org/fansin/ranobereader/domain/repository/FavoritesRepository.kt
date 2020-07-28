package org.fansin.ranobereader.domain.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.fansin.ranobereader.database.FavoritesDao
import org.fansin.ranobereader.domain.model.Novel

class FavoritesRepository(
    private val favoritesDao: FavoritesDao
) {
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

    suspend fun has(novel: Novel) = getById(novel.id) != null

    private suspend fun getById(id: Int): Novel? {
        return withContext(Dispatchers.IO) {
            favoritesDao.getById(id)
        }
    }
}
