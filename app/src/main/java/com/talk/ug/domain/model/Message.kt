package com.talk.ug.domain.model

data class Message(
    val id: String,
    val chatId: String,
    val senderId: String,
    val senderName: String,
    val senderAvatar: String = "",
    val content: String,
    val messageType: MessageType = MessageType.TEXT,
    val mediaUrl: String = "",
    val duration: Long = 0,
    val isRead: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

enum class MessageType {
    TEXT, IMAGE, AUDIO, FILE, VIDEO
}
