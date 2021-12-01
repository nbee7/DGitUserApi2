package com.dgituserapi2.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dgituserapi2.databinding.ItemUserBinding
import com.dgituserapi2.model.UserItems
import com.dgituserapi2.utility.setImageUrl

class UserAdapter(
    val context: Context,
    val data: MutableList<UserItems> = mutableListOf(),
    val listener: OnUserItemListener? = null,
) : RecyclerView.Adapter<UserAdapter.UseHorizontalViewHolder>() {

    inner class UseHorizontalViewHolder(private val view: ItemUserBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(data: UserItems) {
            view.apply {
                imgUser.setImageUrl(context, data.avatarUrl, pbUser)
                tvUsername.text = data.login
                tvId.text = data.id.toString()
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

    override fun onBindViewHolder(holder: UseHorizontalViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    interface OnUserItemListener {
        fun onUserItemClicked(data: UserItems)
    }
}