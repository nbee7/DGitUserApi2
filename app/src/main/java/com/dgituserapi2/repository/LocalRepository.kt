package com.dgituserapi2.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dgituserapi2.database.FavoriteUser
import com.dgituserapi2.database.UserFavoriteDao
import com.dgituserapi2.database.UserFavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LocalRepository(application: Application) {
    private val mUserDao: UserFavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserFavoriteRoomDatabase.getDatabase(application)
        mUserDao = db.userDao()
    }

    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> = mUserDao.getAllFavorite()

    fun checkFavorite(id: String): LiveData<Boolean> = mUserDao.isFavorite(id)

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute { mUserDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: FavoriteUser) {
        executorService.execute { mUserDao.delete(favoriteUser) }
    }
}