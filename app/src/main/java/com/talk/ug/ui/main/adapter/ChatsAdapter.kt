package com.talk.ug.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.talk.ug.domain.model.Chat

class ChatsAdapter(
    private var chats: List<Chat> = emptyList(),
    private val onChatClickListener: (Chat) -> Unit = {}
) : RecyclerView.Adapter<ChatsAdapter.ChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        // val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        // return ChatViewHolder(view)
        return ChatViewHolder(TextView(parent.context))
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(chats[position])
    }

    override fun getItemCount(): Int = chats.size

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // private val profilePic: ImageView = itemView.findViewById(R.id.profile_pic)
        // private val nameText: TextView = itemView.findViewById(R.id.name_text)
        // private val lastMessageText: TextView = itemView.findViewById(R.id.last_message_text)
        // private val timeText: TextView = itemView.findViewById(R.id.time_text)
        // private val unreadBadge: TextView = itemView.findViewById(R.id.unread_badge)

        fun bind(chat: Chat) {
            // nameText.text = chat.participants.firstOrNull()?.name ?: "Unknown"
            // lastMessageText.text = chat.lastMessage
            
            // if (chat.unreadCount > 0) {
            //     unreadBadge.visibility = View.VISIBLE
            //     unreadBadge.text = chat.unreadCount.toString()
            // } else {
            //     unreadBadge.visibility = View.GONE
            // }

            // itemView.setOnClickListener {
            //     onChatClickListener(chat)
            // }
        }
    }

    fun updateChats(newChats: List<Chat>) {
        chats = newChats
        notifyDataSetChanged()
    }
}
