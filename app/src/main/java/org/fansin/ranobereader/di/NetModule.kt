package org.fansin.ranobereader.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import org.fansin.ranobereader.domain.RanobeApi
import org.fansin.ranobereader.domain.model.Novel
import org.fansin.ranobereader.domain.model.NovelDeserializer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val CONNECTION_TIMEOUT: Long = 30000
private const val BASE_URL = "https://xn--80ac9aeh6f.xn--p1ai/api/v2/"

@Module
class NetModule {

    @Singleton
    @Provides
    fun provideNovelDeserializer(): NovelDeserializer {
        return NovelDeserializer()
    }

    @Singleton
    @Provides
    fun provideGson(novelDeserializer: NovelDeserializer): Gson {
        return GsonBuilder().registerTypeAdapter(Novel::class.java, novelDeserializer).create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): RanobeApi {
        return retrofit.create(RanobeApi::class.java)
    }

}