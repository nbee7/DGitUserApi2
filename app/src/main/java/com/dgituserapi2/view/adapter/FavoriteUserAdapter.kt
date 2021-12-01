package com.dgituserapi2.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dgituserapi2.database.FavoriteUser
import com.dgituserapi2.databinding.ItemUserBinding
import com.dgituserapi2.utility.FavoriteDiffCallback
import com.dgituserapi2.utility.setImageUrl

class FavoriteUserAdapter(
    val context: Context,
    val listener: OnUserItemListener? = null,
) : RecyclerView.Adapter<FavoriteUserAdapter.UseHorizontalViewHolder>() {

    private val dataFavorite = ArrayList<FavoriteUser>()
    fun setListFavorite(listFavorite: List<FavoriteUser>) {
        val diffCallback = FavoriteDiffCallback(this.dataFavorite, listFavorite)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.dataFavorite.clear()
        this.dataFavorite.addAll(listFavorite)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class UseHorizontalViewHolder(private val view: ItemUserBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(data: FavoriteUser) {
            view.apply {
                imgUser.setImageUrl(context, data.avatar, pbUser)
                tvUsername.text = data.username
                tvId.text = data.userId
            }
            itemView.setOnClickListener {
                listener?.onUserItemClicked(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UseHorizontalViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UseHorizontalViewHolder(view)
    }

    override fun getItemCount(): Int = dataFavorite.size

    override fun onBindViewHolder(holder: UseHorizontalViewHolder, position: Int) {
        holder.bind(dataFavorite[position])
    }

    interface OnUserItemListener {
        fun onUserItemClicked(data: FavoriteUser)
    }
}