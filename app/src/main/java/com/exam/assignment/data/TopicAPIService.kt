package com.exam.assignment.data

import com.exam.assignment.model.DataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TopicAPIService {
    @GET(".")
    fun getTopics(@Query("q") query: String,
                  @Query("format") format: String): Call<DataResponse>
}
