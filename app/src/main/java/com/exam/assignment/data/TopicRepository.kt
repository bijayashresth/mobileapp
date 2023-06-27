package com.exam.assignment.data

import com.exam.assignment.model.DataResponse
import retrofit2.Call


class TopicRepository(private val apiService: TopicAPIService) {
    fun getRecords(topicNm: String): Call<DataResponse> {
        return apiService.getTopics(topicNm, "json")
    }
}