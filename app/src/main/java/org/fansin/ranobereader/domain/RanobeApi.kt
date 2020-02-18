package org.fansin.ranobereader.domain

import org.fansin.ranobereader.domain.model.Novel
import org.fansin.ranobereader.domain.model.ResultList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RanobeApi {

    @GET("books1?page=1&pageSize=16")
    fun getBooks(@Query("page") page: Int,
                 @Query("pageSize") pageSize: Int): Call<ResultList<Novel>>

}
