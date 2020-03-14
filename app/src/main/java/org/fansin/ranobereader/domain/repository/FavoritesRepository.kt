package org.fansin.ranobereader.domain.repository

import org.fansin.ranobereader.database.FavoritesDao
import org.fansin.ranobereader.domain.model.Novel

class FavoritesRepository(
    private val favoritesDao: FavoritesDao
) {

    fun getFavorites(): List<Novel> {
        return favoritesDao.getAll()
    }
}
