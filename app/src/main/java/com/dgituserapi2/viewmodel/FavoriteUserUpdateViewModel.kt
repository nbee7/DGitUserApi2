package com.dgituserapi2.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dgituserapi2.database.FavoriteUser
import com.dgituserapi2.repository.LocalRepository

class FavoriteUserUpdateViewModel(application: Application) : ViewModel() {

    private val mLocalRepository: LocalRepository =
        LocalRepository(application)

    fun insert(favoriteUser: FavoriteUser) {
        mLocalRepository.insert(favoriteUser)
    }

    fun delete(favoriteUser: FavoriteUser) {
        mLocalRepository.delete(favoriteUser)
    }

    fun checkFavorite(id: String): LiveData<Boolean> =
        mLocalRepository.checkFavorite(id)

}