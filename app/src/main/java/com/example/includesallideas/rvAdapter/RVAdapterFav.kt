package com.example.includesallideas.rvAdapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.includesallideas.FavoriteFragments
import com.example.includesallideas.R
import com.example.includesallideas.roomDatabase.UserEntity

import kotlinx.android.synthetic.main.item_row2.view.*

class RVAdapterFav (private val favoriteUser: FavoriteFragments, private val users: List<UserEntity>) : RecyclerView.Adapter<RVAdapterFav.ItemViewHolder>(){
    class ItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    private var ctx : Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        ctx=parent.context
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row2
                ,parent
                ,false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val user = users[position]

        holder.itemView.apply{

            tvNote.text= "${user.id}\n${user.name}\n${user.location}"
            ibDeleteNote.setOnClickListener {
               favoriteUser.listViewModel.deleteUser(user.id)
            }
        }
    }

    override fun getItemCount(): Int =users.size

}