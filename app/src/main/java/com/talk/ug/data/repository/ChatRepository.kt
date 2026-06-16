package com.talk.ug.data.repository

import com.talk.ug.data.remote.SupabaseClient
import com.talk.ug.domain.model.Chat
import com.talk.ug.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID

class ChatRepository {

    suspend fun createChat(
        participantIds: List<String>,
        lastMessage: String = ""
    ): Flow<Result<Chat>> = flow {
        try {
            val chatId = UUID.randomUUID().toString()
            
            SupabaseClient.db.from("chats")
                .insert(mapOf(
                    "id" to chatId,
                    "participant_ids" to participantIds,
                    "last_message" to lastMessage,
                    "last_message_time" to System.currentTimeMillis()
                ))
            
            emit(Result.success(Chat(
                id = chatId,
                participants = emptyList(),
                lastMessage = lastMessage,
                lastMessageTime = System.currentTimeMillis()
            )))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun getChats(userId: String): Flow<Result<List<Chat>>> = flow {
        try {
            val response = SupabaseClient.db.from("chats")
                .select()
                .order("last_message_time", ascending = false)
            
            @Suppress("UNCHECKED_CAST")
            val chats = (response as List<Map<String, Any>>).map { data ->
                Chat(
                    id = data["id"] as String,
                    participants = emptyList(),
                    lastMessage = data["last_message"] as? String ?: "",
                    lastMessageTime = (data["last_message_time"] as? Number)?.toLong() ?: 0L,
                    unreadCount = (data["unread_count"] as? Number)?.toInt() ?: 0
                )
            }
            emit(Result.success(chats))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun updateChatLastMessage(
        chatId: String,
        lastMessage: String
    ): Flow<Result<Unit>> = flow {
        try {
            SupabaseClient.db.from("chats")
                .update(mapOf(
                    "last_message" to lastMessage,
                    "last_message_time" to System.currentTimeMillis()
                ))
                .eq("id", chatId)
            
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun deleteChat(chatId: String): Flow<Result<Unit>> = flow {
        try {
            SupabaseClient.db.from("chats")
                .delete()
                .eq("id", chatId)
            
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
