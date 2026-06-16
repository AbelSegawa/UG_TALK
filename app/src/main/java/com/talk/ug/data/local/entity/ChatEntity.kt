package com.talk.ug.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chats")
data class ChatEntity(
    @PrimaryKey
    val id: String,
    val participantIds: List<String>, // User IDs involved in chat
    val lastMessage: String = "",
    val lastMessageTime: Long = 0,
    val unreadCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)
