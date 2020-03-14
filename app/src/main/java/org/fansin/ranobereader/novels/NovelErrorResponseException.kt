package org.fansin.ranobereader.novels

class NovelErrorResponseException(private val code: Int) : Exception() {
    override val message: String
        get() = "Error code: $code"
}
