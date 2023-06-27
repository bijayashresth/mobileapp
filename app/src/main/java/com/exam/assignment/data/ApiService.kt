package com.exam.assignment.data

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private var retrofitBaseApi: Retrofit? = null

    fun getBaseApi(): TopicAPIService? {
        return createRetrofitBase().create(TopicAPIService::class.java)
    }

    private fun createRetrofitBase(): Retrofit {
        if (retrofitBaseApi == null) {
            retrofitBaseApi = getOkHttpClient()?.let {
                Retrofit.Builder()
                    .baseUrl("http://api.duckduckgo.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(it)
                    .build()
            }
        }
        return retrofitBaseApi as Retrofit
    }

    private fun getOkHttpClient(): OkHttpClient? {
        val client = OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
            .addInterceptor { chain ->
                val originalRequest: Request = chain.request()
                val requestBuilder = originalRequest.newBuilder()
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
        return client
    }
}
