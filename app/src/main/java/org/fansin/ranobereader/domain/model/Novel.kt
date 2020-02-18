package org.fansin.ranobereader.domain.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import org.fansin.ranobereader.domain.model.Chapter

@Entity
data class Novel(
    @PrimaryKey val id: Int,
    val title: String,
    val author: String,
    val genres: List<String>,
    @Ignore val chapters: MutableList<Chapter>,
    val imageUrl: String,
    val description: String
) {
    var isFavorite = false
    var lastReadChapter = 0
}
