package org.fansin.ranobereader.domain.utils

import androidx.room.TypeConverter
import org.fansin.ranobereader.domain.model.Chapter
import org.fansin.ranobereader.domain.model.Image
import org.fansin.ranobereader.domain.model.Images

class NovelTypeConverter {

    @TypeConverter
    fun fromGenres(genres: List<String>) = genres.joinToString(",")

    @TypeConverter
    fun toGenres(genres: String): List<String> = genres.split(",")

    @TypeConverter
    fun fromImages(images: Images) = images.images.joinToString(",", transform = Image::url)

    @TypeConverter
    fun toImages(images: String): Images = Images(images.split(",").map {
        Image(it)
    })

    @TypeConverter
    fun fromChapters(chapters: MutableList<Chapter>) =
        chapters.joinToString(";", transform = Chapter::toString)

    @TypeConverter
    fun toChapters(chapters: String): MutableList<Chapter> =
        if (chapters.isNotEmpty()) {
            chapters.split(";").map {
                val fields = it.split(",")
                Chapter(fields[0].toInt(), fields[1])
            }.toMutableList()
        } else {
            mutableListOf()
        }
}
