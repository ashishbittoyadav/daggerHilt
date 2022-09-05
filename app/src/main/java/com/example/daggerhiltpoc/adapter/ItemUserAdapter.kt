package com.example.daggerhiltpoc.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.daggerhiltpoc.R
import com.example.daggerhiltpoc.databinding.ItemUserDetailBinding
import com.example.daggerhiltpoc.model.UsersItem

class ItemUserAdapter(private val context: Context,private val lifecycleOwner: LifecycleOwner,private val userList: List<UsersItem>) : RecyclerView.Adapter<ItemUserAdapter.ItemUserViewHolder>() {

    private val TAG = "ItemUserAdapter.TAG"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemUserViewHolder {

//        return ItemUserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user_detail,parent,false))
        return ItemUserViewHolder(ItemUserDetailBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemUserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class ItemUserViewHolder(private val inflate: ItemUserDetailBinding) : RecyclerView.ViewHolder(inflate.root){

        fun bind(usersItem: UsersItem) {
            inflate.userData = usersItem
            inflate.lifecycleOwner = lifecycleOwner
            inflate.executePendingBindings()


        }

    }
}