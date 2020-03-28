package org.fansin.ranobereader.domain.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.fansin.ranobereader.ApplicationConfig
import org.fansin.ranobereader.domain.model.Novel
import org.fansin.ranobereader.domain.model.NovelResultList
import org.fansin.ranobereader.network.RanobeApi
import org.fansin.ranobereader.ui.novels.NovelErrorResponseException
import org.fansin.ranobereader.ui.novels.NovelsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NovelsRepository(
    private val ranobeApi: RanobeApi
) {

    private var retries = 0

    interface NovelCallback {
        fun onResult(data: List<Novel>)
        fun onError(t: Throwable)
    }

    fun loadData(novelsResponse: NovelsResponse) {
        Log.d("Loaded", "page = ${novelsResponse.page}, count = ${novelsResponse.count}")
        ranobeApi
            .getBooks(novelsResponse.page, novelsResponse.count)
            .enqueue(createCallback(novelsResponse))
    }

    private fun createCallback(novelsResponse: NovelsResponse) =
        object : Callback<NovelResultList> {
            override fun onFailure(call: Call<NovelResultList>, t: Throwable) {
                onError(novelsResponse, t)
            }

            override fun onResponse(
                call: Call<NovelResultList>,
                response: Response<NovelResultList>
            ) {
                if (response.isSuccessful) {
                    novelsResponse.novelCallback.onResult(response.body()?.items ?: listOf())
                } else {
                    onError(novelsResponse, NovelErrorResponseException(response.code()))
                }
            }
        }

    private fun onError(novelsResponse: NovelsResponse, throwable: Throwable) =
        GlobalScope.launch(Dispatchers.Default) {
            delay(ApplicationConfig.NOVEL_RETRY_DELAY)
            if (retries > ApplicationConfig.NOVEL_LOAD_MAX_RETRIES) {
                retries = 0
                novelsResponse.novelCallback.onError(throwable)
            } else {
                retries++
                loadData(novelsResponse)
            }
        }
}
