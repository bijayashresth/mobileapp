package com.exam.assignment.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.exam.assignment.data.ApiService
import com.exam.assignment.model.DataResponse
import com.exam.assignment.data.TopicRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopicsViewModel : ViewModel() {

    val dictionaryResponseLiveData = MutableLiveData<DataResponse>()
    var progressLiveData = MutableLiveData<Boolean>()
    var showAlertLiveData = MutableLiveData<String>()

    private val repository = ApiService.getBaseApi()?.let {
        TopicRepository(it)
    }

    fun getRecords(name :String) {
        progressLiveData.postValue(true)
        repository?.getRecords(name)?.enqueue(object : Callback<DataResponse> {

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                progressLiveData.postValue(false)
                showAlertLiveData.postValue(t.localizedMessage?.toString() ?: "N/A")
            }

            override fun onResponse(
                call: Call<DataResponse>, response: Response<DataResponse>
            ) {
                progressLiveData.postValue(false)
                dictionaryResponseLiveData.postValue(
                    response.body()
                )
            }
        })
    }
}
