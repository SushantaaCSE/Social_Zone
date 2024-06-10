package com.example.chatdrid.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.chatdrid.R
import com.example.chatdrid.activity.SmsActivity
import com.example.chatdrid.databinding.UserChatBinding
import com.example.chatdrid.model.userModel

class ChatAdapter (var context: Context, var list: ArrayList<userModel>): RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    inner class ChatViewHolder(view:View):RecyclerView.ViewHolder(view){
        var binding:UserChatBinding=UserChatBinding.bind(view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
       return ChatViewHolder(LayoutInflater.from(parent.context)
           .inflate(R.layout.user_chat,parent,false))
    }
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        var user=list[position]
        Glide.with(context).load(user.imageUrl).into(holder.binding.userImage)
        holder.binding.username.text=user.name
        holder.itemView.setOnClickListener{
            val intent=Intent(context,SmsActivity::class.java)
            intent.putExtra("uid",user.uid)
            intent.putExtra("name",user.name)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return list.size
    }


}