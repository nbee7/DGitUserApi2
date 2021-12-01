package com.dgituserapi2.viewmodel


import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dgituserapi2.BuildConfig
import com.dgituserapi2.model.UserItems
import com.dgituserapi2.model.UserResponse
import com.dgituserapi2.model.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val listUserSearch = MutableLiveData<List<UserItems>>()
    private val token = "token " + BuildConfig.API_KEY

    fun searchUser(username: String) {
        val client = ApiConfig.getApiService().searchUser(username, token)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    listUserSearch.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getListSearch(): LiveData<List<UserItems>> {
        return listUserSearch
    }
}