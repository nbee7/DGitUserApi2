package com.dgituserapi2.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dgituserapi2.BuildConfig
import com.dgituserapi2.model.UserDetailResponse
import com.dgituserapi2.model.UserItems
import com.dgituserapi2.model.UserRepositoryResponse
import com.dgituserapi2.model.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {
    private val detailUser = MutableLiveData<UserDetailResponse>()
    private val repository = MutableLiveData<List<UserRepositoryResponse>>()
    private val followers = MutableLiveData<List<UserItems>>()
    private val followings = MutableLiveData<List<UserItems>>()

    private val token = "token " + BuildConfig.API_KEY

    fun detailUser(username: String) {
        val client = ApiConfig.getApiService().getDetail(username, token)
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                    call: Call<UserDetailResponse>,
                    response: Response<UserDetailResponse>
            ) {
                if (response.isSuccessful) {
                    detailUser.value = response.body()
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun userFollowers(username: String) {
        val client = ApiConfig.getApiService().getFollowers(username, token)
        client.enqueue(object : Callback<List<UserItems>> {
            override fun onResponse(
                    call: Call<List<UserItems>>,
                    response: Response<List<UserItems>>
            ) {
                if (response.isSuccessful) {
                    followers.value = response.body()
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserItems>>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun userFollowings(username: String) {
        val client = ApiConfig.getApiService().getFollowings(username, token)
        client.enqueue(object : Callback<List<UserItems>> {
            override fun onResponse(
                    call: Call<List<UserItems>>,
                    response: Response<List<UserItems>>
            ) {
                if (response.isSuccessful) {
                    followings.value = response.body()
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserItems>>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun userRepository(username: String) {
        val client = ApiConfig.getApiService().getRepository(username, token)
        client.enqueue(object : Callback<List<UserRepositoryResponse>> {
            override fun onResponse(
                    call: Call<List<UserRepositoryResponse>>,
                    response: Response<List<UserRepositoryResponse>>
            ) {
                if (response.isSuccessful) {
                    repository.value = response.body()
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserRepositoryResponse>>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getDetail(): LiveData<UserDetailResponse> {
        return detailUser
    }

    fun getFollowers(): LiveData<List<UserItems>> {
        return followers
    }

    fun getFollowings(): LiveData<List<UserItems>> {
        return followings
    }

    fun getRepository(): MutableLiveData<List<UserRepositoryResponse>> {
        return repository
    }
}