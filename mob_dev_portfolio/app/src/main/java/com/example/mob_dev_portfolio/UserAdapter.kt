package com.example.mob_dev_portfolio

import User
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(
    private val userList: List<User>,
    param: (Any) -> Unit, ) : RecyclerView.Adapter<UserAdapter.UserViewHolder>()
{
    var onItemClick: ((User) -> Unit?)? = null

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.name_text)
        val userImage: ImageView = itemView.findViewById(R.id.love_button)
        val userButton: ImageButton = itemView.findViewById(R.id.share_button)
        val favButton: ImageButton = itemView.findViewById(R.id.love_button)
        val location: TextView = itemView.findViewById(R.id.location_text)
        val skill: TextView = itemView.findViewById(R.id.skill_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_card_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(userList[position])
            Toast.makeText(holder.itemView.context, "User clicked ${userList[position].name}",
                Toast.LENGTH_SHORT).show()
        }
        val user = userList[position]
        holder.userName.text = user.name
        holder.location.text = user.location
        holder.skill.text = user.skill
        // TODO 1: HANDLE THE CLICK EVENT OF THE SHARE BUTTON
        holder.userButton.setOnClickListener {

        }

        // TODO 2: HANDLE THE CLICK EVENT OF THE FAV BUTTON
        holder.favButton.setOnClickListener {

        }

    }
}