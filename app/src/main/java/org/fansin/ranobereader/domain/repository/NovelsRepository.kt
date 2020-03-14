package org.fansin.ranobereader.domain.repository

import android.util.Log
import org.fansin.ranobereader.domain.model.Novel
import org.fansin.ranobereader.domain.model.NovelResultList
import org.fansin.ranobereader.network.RanobeApi
import org.fansin.ranobereader.novels.NovelErrorResponseException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NovelsRepository(
    private val ranobeApi: RanobeApi
) {

    interface NovelCallback {
        fun onResult(data: List<Novel>)
        fun onError(t: Throwable)
    }

    fun loadData(page: Int, count: Int, novelCallback: NovelCallback) {
        Log.d("Loaded", "page = $page, count = $count")
        ranobeApi.getBooks(page, count).enqueue(object : Callback<NovelResultList> {
            override fun onFailure(call: Call<NovelResultList>, t: Throwable) {
                novelCallback.onError(t)
            }

            override fun onResponse(
                call: Call<NovelResultList>,
                response: Response<NovelResultList>
            ) {
                if (response.isSuccessful) {
                    novelCallback.onResult(response.body()?.items ?: listOf())
                } else {
                    novelCallback.onError(NovelErrorResponseException(response.code()))
                }
            }
        })
    }
}
