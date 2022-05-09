package com.example.stuffy.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.stuffy.core.domain.model.User
import com.example.stuffy.core.databinding.ItemRowUserBinding
import com.example.stuffy.core.domain.model.Filter


class ListUserAdapter(private val listUser: ArrayList<User>) :
    RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    var onItemClick: ((User) -> Unit)? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user)

    }

    override fun getItemCount(): Int = listUser.size
    inner class ListViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user:User) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(user.avatar)

                    .into(imgItemPhoto)
                tvItemName.text = user.username
                tvItemDescription.text = user.name
            }
        }
        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listUser[absoluteAdapterPosition])
            }
        }
    }



}

