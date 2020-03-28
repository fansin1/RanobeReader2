package org.fansin.ranobereader.domain.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import kotlinx.android.parcel.Parcelize
import org.fansin.ranobereader.domain.utils.NovelTypeConverter

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(tableName = "novel")
@TypeConverters(NovelTypeConverter::class)
data class Novel(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val title: String,
    @Embedded val author: Author = Author(0, ""),
    val genres: List<String> = listOf(),
    val chapters: MutableList<Chapter> = mutableListOf(),
    val images: List<Image> = listOf(),
    val description: String = "",
    val likesCount: Int = 0,
    val dislikesCount: Int = 0
) : Parcelable
