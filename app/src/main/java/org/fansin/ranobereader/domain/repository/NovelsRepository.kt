package org.fansin.ranobereader.domain.repository

import android.util.Log
import org.fansin.ranobereader.domain.RanobeApi
import org.fansin.ranobereader.domain.model.Novel
import org.fansin.ranobereader.domain.model.ResultList
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
        ranobeApi.getBooks(page, count).enqueue(object : Callback<ResultList<Novel>> {
            override fun onFailure(call: Call<ResultList<Novel>>, t: Throwable) {
                novelCallback.onError(t)
            }

            override fun onResponse(
                call: Call<ResultList<Novel>>,
                response: Response<ResultList<Novel>>
            ) {
                novelCallback.onResult(response.body()?.items ?: listOf())
            }
        })
    }
}
