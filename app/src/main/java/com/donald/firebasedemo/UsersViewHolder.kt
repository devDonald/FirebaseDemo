package com.donald.firebasedemo

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.all_users.view.*

class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindUsers(userModel: UserModel?) {

        with(userModel!!) {
            itemView.person_name.text = names
            itemView.person_email.text = email
            itemView.person_phone.text = phone

        }
    }
}