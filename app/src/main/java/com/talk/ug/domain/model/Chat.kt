package com.talk.ug.domain.model

data class Chat(
    val id: String,
    val participants: List<User>,
    val lastMessage: String = "",
    val lastMessageTime: Long = 0,
    val unreadCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)
