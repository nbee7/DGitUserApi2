package com.dgituserapi2.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dgituserapi2.database.FavoriteUser
import com.dgituserapi2.repository.LocalRepository

class FavoriteUserViewModel(application: Application) : ViewModel() {

    private val mLocalRepository: LocalRepository = LocalRepository(application)

    fun getAllListFavorite(): LiveData<List<FavoriteUser>> =
        mLocalRepository.getAllFavoriteUser()
}