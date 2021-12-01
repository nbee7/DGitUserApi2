package com.dgituserapi2.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dgituserapi2.databinding.ItemRepositoryBinding
import com.dgituserapi2.model.UserRepositoryResponse
import com.dgituserapi2.utility.toShortNumberDisplay

class RepositoryAdapter(
    val data: MutableList<UserRepositoryResponse> = mutableListOf(),
    val listener: OnUserItemListener? = null,
) : RecyclerView.Adapter<RepositoryAdapter.UseHorizontalViewHolder>() {

    inner class UseHorizontalViewHolder(private val view: ItemRepositoryBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(data: UserRepositoryResponse) {
            view.apply {
                tvTitle.text = data.name
                tvDesc.text = data.description
                tvLanguage.text = data.language
                tvStarCount.text = data.starsCount.toShortNumberDisplay()
                tvWatchersCount.text = data.watchersCount.toShortNumberDisplay()
                tvForkCount.text = data.forksCount.toShortNumberDisplay()
            }
            itemView.setOnClickListener {
                listener?.onUserItemClicked(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UseHorizontalViewHolder {
        val view = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UseHorizontalViewHolder(view)
    }

    override fun onBindViewHolder(holder: UseHorizontalViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    interface OnUserItemListener {
        fun onUserItemClicked(data: UserRepositoryResponse)
    }


}