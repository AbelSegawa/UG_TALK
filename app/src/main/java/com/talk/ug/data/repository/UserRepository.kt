package com.talk.ug.data.repository

import com.talk.ug.data.remote.SupabaseClient
import com.talk.ug.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository {

    suspend fun getUser(userId: String): Flow<Result<User>> = flow {
        try {
            val response = SupabaseClient.db.from("users")
                .select()
                .eq("id", userId)
                .limit(1)
                .single()
            
            val userData = response as Map<String, Any>
            emit(Result.success(mapToUser(userData)))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun searchUsers(query: String): Flow<Result<List<User>>> = flow {
        try {
            val response = SupabaseClient.db.rpc(
                "search_users",
                mapOf("search_query" to query)
            )
            
            @Suppress("UNCHECKED_CAST")
            val users = (response as List<Map<String, Any>>).map { mapToUser(it) }
            emit(Result.success(users))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun updateUserProfile(
        userId: String,
        name: String? = null,
        bio: String? = null,
        profilePicUrl: String? = null
    ): Flow<Result<Unit>> = flow {
        try {
            val updateMap = mutableMapOf<String, Any>()
            name?.let { updateMap["name"] = it }
            bio?.let { updateMap["bio"] = it }
            profilePicUrl?.let { updateMap["profile_pic_url"] = it }
            updateMap["updated_at"] = System.currentTimeMillis()
            
            SupabaseClient.db.from("users")
                .update(updateMap)
                .eq("id", userId)
            
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun updateUserStatus(userId: String, isOnline: Boolean): Flow<Result<Unit>> = flow {
        try {
            SupabaseClient.db.from("users")
                .update(mapOf(
                    "is_online" to isOnline,
                    "last_seen" to System.currentTimeMillis()
                ))
                .eq("id", userId)
            
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    private fun mapToUser(data: Map<String, Any>): User {
        return User(
            id = data["id"] as String,
            email = data["email"] as String,
            name = data["name"] as String,
            phone = data["phone"] as String,
            bio = data["bio"] as? String ?: "",
            profilePicUrl = data["profile_pic_url"] as? String ?: "",
            isOnline = data["is_online"] as? Boolean ?: false,
            lastSeen = (data["last_seen"] as? Number)?.toLong() ?: 0L
        )
    }
}
