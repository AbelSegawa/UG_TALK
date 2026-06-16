package com.talk.ug.data.repository

import com.talk.ug.data.remote.SupabaseClient
import com.talk.ug.domain.model.Message
import com.talk.ug.domain.model.MessageType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID

class MessageRepository {

    suspend fun sendMessage(
        chatId: String,
        senderId: String,
        receiverId: String,
        content: String,
        messageType: MessageType = MessageType.TEXT,
        mediaUrl: String = ""
    ): Flow<Result<Message>> = flow {
        try {
            val messageId = UUID.randomUUID().toString()
            val timestamp = System.currentTimeMillis()
            
            SupabaseClient.db.from("messages")
                .insert(mapOf(
                    "id" to messageId,
                    "chat_id" to chatId,
                    "sender_id" to senderId,
                    "receiver_id" to receiverId,
                    "content" to content,
                    "message_type" to messageType.name,
                    "media_url" to mediaUrl,
                    "created_at" to timestamp
                ))
            
            emit(Result.success(Message(
                id = messageId,
                chatId = chatId,
                senderId = senderId,
                senderName = "",
                content = content,
                messageType = messageType,
                mediaUrl = mediaUrl,
                timestamp = timestamp
            )))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun getMessages(chatId: String, limit: Int = 50): Flow<Result<List<Message>>> = flow {
        try {
            val response = SupabaseClient.db.rpc(
                "get_chat_messages",
                mapOf(
                    "p_chat_id" to chatId,
                    "p_limit" to limit
                )
            )
            
            @Suppress("UNCHECKED_CAST")
            val messages = (response as List<Map<String, Any>>).map { data ->
                Message(
                    id = data["message_id"] as String,
                    chatId = chatId,
                    senderId = data["sender_id"] as String,
                    senderName = data["sender_name"] as String,
                    content = data["content"] as String,
                    messageType = MessageType.valueOf(data["message_type"] as String),
                    mediaUrl = data["media_url"] as? String ?: "",
                    isRead = data["is_read"] as? Boolean ?: false,
                    timestamp = (data["created_at"] as? String)?.toLong() ?: 0L
                )
            }
            emit(Result.success(messages))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun markAsRead(messageId: String): Flow<Result<Unit>> = flow {
        try {
            SupabaseClient.db.from("messages")
                .update(mapOf("is_read" to true))
                .eq("id", messageId)
            
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun deleteMessage(messageId: String): Flow<Result<Unit>> = flow {
        try {
            SupabaseClient.db.from("messages")
                .delete()
                .eq("id", messageId)
            
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
