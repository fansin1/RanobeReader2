package org.fansin.ranobereader

import org.fansin.ranobereader.domain.model.Chapter
import org.fansin.ranobereader.domain.model.Image
import org.fansin.ranobereader.domain.utils.NovelTypeConverter
import org.junit.Assert.assertEquals
import org.junit.Test

class NovelTypeConverterTest {

    private val imagesString = "https://google.com/kek.jpg,https://google.com/lol.jpg"
    private val images = listOf(
        Image("https://google.com/kek.jpg"),
        Image("https://google.com/lol.jpg")
    )

    private val chaptersString = "0,Kek;1,lol"
    private val chapters = mutableListOf(
        Chapter(0, "Kek"),
        Chapter(1, "lol")
    )
    private val underTest = NovelTypeConverter()

    @Test
    fun fromImages() {
        assertEquals(imagesString, underTest.fromImages(images))
    }

    @Test
    fun toImages() {
        assertEquals(images, underTest.toImages(imagesString))
    }

    @Test
    fun fromChapters() {
        assertEquals(chaptersString, underTest.fromChapters(chapters))
    }

    @Test
    fun toChapters() {
        assertEquals(chapters, underTest.toChapters(chaptersString))
    }
}
