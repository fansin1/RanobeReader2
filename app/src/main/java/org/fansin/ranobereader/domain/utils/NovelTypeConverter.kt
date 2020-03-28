package org.fansin.ranobereader.domain.utils

import androidx.room.TypeConverter
import org.fansin.ranobereader.domain.model.Chapter
import org.fansin.ranobereader.domain.model.Image

class NovelTypeConverter {

    @TypeConverter
    fun fromGenres(genres: List<String>) = genres.joinToString(",")

    @TypeConverter
    fun toGenres(genres: String): List<String> = genres.split(",")

    @TypeConverter
    fun fromImages(images: List<Image>) = images.joinToString(",", transform = Image::url)

    @TypeConverter
    fun toImages(images: String): List<Image> = images.split(",").map {
        Image(it)
    }

    @TypeConverter
    fun fromChapters(chapters: MutableList<Chapter>) =
        chapters.joinToString(";", transform = Chapter::toString)

    @TypeConverter
    fun toChapters(chapters: String): MutableList<Chapter> =
        chapters.split(";").map {
            val fields = it.split(",")
            Chapter(fields[0].toInt(), fields[1])
        }.toMutableList()
}
