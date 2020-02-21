package org.fansin.ranobereader.novels

interface NovelsNetworkListener {

    fun onLoading()

    fun onLoadInitCompleted()

    fun onLoadAfterCompleted()

    fun onLoadInitError()

    fun onLoadAfterError()

}