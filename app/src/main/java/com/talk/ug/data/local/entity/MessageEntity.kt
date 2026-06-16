package com.talk.ug.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey
    val id: String,
    val chatId: String,
    val senderId: String,
    val receiverId: String,
    val content: String,
    val messageType: String, // TEXT, IMAGE, AUDIO, FILE
    val mediaUrl: String = "",
    val duration: Long = 0, // For audio messages
    val isRead: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
