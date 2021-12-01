package com.dgituserapi2.utility

import androidx.recyclerview.widget.DiffUtil
import com.dgituserapi2.database.FavoriteUser

class FavoriteDiffCallback(
    private val OldFavList: List<FavoriteUser>,
    private val NewFavList: List<FavoriteUser>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return OldFavList.size
    }

    override fun getNewListSize(): Int {
        return NewFavList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return OldFavList[oldItemPosition].userId == NewFavList[newItemPosition].userId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = OldFavList[oldItemPosition]
        val newEmployee = NewFavList[newItemPosition]
        return oldEmployee.userId == newEmployee.userId && oldEmployee.username == newEmployee.username
    }

}