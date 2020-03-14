package org.fansin.ranobereader.network

import org.fansin.ranobereader.domain.model.NovelResultList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RanobeApi {

    @GET("books?page=1&pageSize=16")
    fun getBooks(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Call<NovelResultList>
}
