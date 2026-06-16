package com.talk.ug.ui.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.talk.ug.domain.model.Message

class MessagesAdapter(
    private var messages: List<Message> = emptyList()
) : RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        // val layoutId = if (viewType == MESSAGE_TYPE_SENT) {
        //     R.layout.item_message_sent
        // } else {
        //     R.layout.item_message_received
        // }
        // val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        // return MessageViewHolder(view)
        return MessageViewHolder(TextView(parent.context))
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int = messages.size

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].senderId == "current_user_id") {
            MESSAGE_TYPE_SENT
        } else {
            MESSAGE_TYPE_RECEIVED
        }
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // private val messageText: TextView = itemView.findViewById(R.id.message_text)
        // private val timeText: TextView = itemView.findViewById(R.id.time_text)
        // private val mediaView: ImageView? = itemView.findViewById(R.id.media_view)

        fun bind(message: Message) {
            // messageText.text = message.content
            // timeText.text = formatTime(message.timestamp)
        }

        private fun formatTime(timestamp: Long): String {
            // Format timestamp to readable time
            return ""
        }
    }

    fun updateMessages(newMessages: List<Message>) {
        messages = newMessages
        notifyDataSetChanged()
    }

    companion object {
        const val MESSAGE_TYPE_SENT = 1
        const val MESSAGE_TYPE_RECEIVED = 2
    }
}
