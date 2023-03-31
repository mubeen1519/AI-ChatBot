package com.example.aichatbot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(private val messageList: List<Message>) : RecyclerView.Adapter<MessageAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val chatView: View = LayoutInflater.from(parent.context).inflate(R.layout.chat_item, null)
        return MyViewHolder(chatView)

    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val message : Message = messageList[position]
        if(message.sentBy == message.SENT_BY_ME){
            holder.leftChatView.visibility = View.GONE
            holder.rightChatView.visibility = View.VISIBLE
            holder.rightChatText.text = message.message
        } else {
            holder.leftChatView.visibility = View.VISIBLE
            holder.rightChatView.visibility = View.GONE
            holder.leftChatText.text = message.message
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val leftChatView : LinearLayout = itemView.findViewById(R.id.left_chat_view)
        val rightChatView : LinearLayout = itemView.findViewById(R.id.right_chat_view)
        val rightChatText : TextView = itemView.findViewById(R.id.right_chat_text)
        val leftChatText : TextView = itemView.findViewById(R.id.left_chat_text)

    }
}