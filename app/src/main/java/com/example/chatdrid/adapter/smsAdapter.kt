package com.example.chatdrid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chatdrid.R
import com.example.chatdrid.databinding.ReciverSmsLayoutBinding
import com.example.chatdrid.databinding.SendSmsLayoutBinding
import com.example.chatdrid.model.smsModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class smsAdapter(var context: Context, var list:ArrayList<smsModel>):RecyclerView.Adapter<ViewHolder>() {
    var item_sent=1
    var item_recieve=2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return if (viewType==item_sent)
        SentViewHolder(LayoutInflater.from(context).inflate(R.layout.send_sms_layout,parent,false
        ))
        else ReceiverViewHolder(
        LayoutInflater.from(context).inflate(R.layout.send_sms_layout,parent,false
        )

        )
    }

    override fun getItemViewType(position: Int): Int {
            return if (FirebaseAuth.getInstance().uid==list[position].senderId)item_sent else item_recieve
    }
    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val msg=list[position]
        if (holder.itemViewType==item_sent){
            val viewHolder=holder as SentViewHolder
            viewHolder.binding.userMsg.text=msg.message
        }
        else{
            val viewHolder=holder as ReceiverViewHolder

            viewHolder.binding.userMsg.text=msg.message

        }

    }

    inner class SentViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var binding=SendSmsLayoutBinding.bind(view)
    }
    inner class ReceiverViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var binding=ReciverSmsLayoutBinding.bind(view)
    }

}
