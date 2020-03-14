package org.fansin.ranobereader.di

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import org.fansin.ranobereader.ApplicationConfig.BASE_URL
import org.fansin.ranobereader.ApplicationConfig.CONNECTION_TIMEOUT
import org.fansin.ranobereader.network.ConnectionLiveData
import org.fansin.ranobereader.network.RanobeApi
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetModule {

    @Singleton
    @Provides
    fun provideConnectionLiveData(context: Context): ConnectionLiveData {
        return ConnectionLiveData(context)
    }

    @Singleton
    @Provides
    fun provideJacksonObjectMapper(): ObjectMapper {
        return jacksonObjectMapper().apply {
            registerKotlinModule()
        }
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
    fun provideRetrofit(okHttpClient: OkHttpClient, mapper: ObjectMapper): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create(mapper))
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): RanobeApi {
        return retrofit.create(RanobeApi::class.java)
    }
}
