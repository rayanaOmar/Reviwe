package com.example.includesallideas.rvAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.includesallideas.R
import com.example.includesallideas.mainFragments
import com.example.includesallideas.retrofit.Users
import com.example.includesallideas.roomDatabase.UserEntity
import kotlinx.android.synthetic.main.item_row.view.*

class RVAdapter (private var listFragments: mainFragments,private var users: ArrayList<Users>): RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val user = users[position]

        holder.itemView.apply {
            tvNote.text = "${user.pk}\n${user.name}\n${user.location}"

            ibEditNote.setOnClickListener {
                with(listFragments.sharedPreferences.edit()) {
                    putInt("NoteId", user.pk)
                    apply()
                }
                listFragments.findNavController()
                    .navigate(R.id.action_mainFragments_to_updateDeleteFragments)
            }

            //DELETE THE USER
            ibDeleteNote.setOnClickListener {
                listFragments.findNavController()
                    .navigate(R.id.action_mainFragments_to_updateDeleteFragments)
            }

            //GO TO FAVORITE FRAGMENTS
            ibFavorite.setOnClickListener {
                ibFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                listFragments.findNavController()
                    .navigate(R.id.action_mainFragments_to_favoriteFragments)

            }
        }
    }

    override fun getItemCount() = users.size

    @SuppressLint("NotifyDataSetChanged")
    fun update(notes: List<UserEntity>) {
        this.users = users
        notifyDataSetChanged()
    }
}