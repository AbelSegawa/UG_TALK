package com.talk.ug.data.repository

import com.talk.ug.data.remote.SupabaseClient
import com.talk.ug.domain.model.AuthUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepository {

    suspend fun signUp(
        email: String,
        password: String,
        name: String,
        phone: String
    ): Flow<Result<AuthUser>> = flow {
        try {
            val response = SupabaseClient.auth.signUpWith(
                email = email,
                password = password
            )
            
            val userId = response.user?.id ?: throw Exception("User creation failed")
            
            // Create user profile in database
            SupabaseClient.db.from("users")
                .insert(
                    mapOf(
                        "id" to userId,
                        "email" to email,
                        "name" to name,
                        "phone" to phone
                    )
                )
            
            emit(Result.success(AuthUser(
                id = userId,
                email = email,
                name = name,
                phone = phone
            )))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun login(
        email: String,
        password: String
    ): Flow<Result<AuthUser>> = flow {
        try {
            val response = SupabaseClient.auth.signInWithPassword(
                email = email,
                password = password
            )
            
            val user = response.user
            if (user != null) {
                emit(Result.success(AuthUser(
                    id = user.id,
                    email = user.email ?: "",
                    name = "",
                    phone = ""
                )))
            } else {
                emit(Result.failure(Exception("Login failed")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun logout(): Flow<Result<Unit>> = flow {
        try {
            SupabaseClient.auth.signOut()
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun getCurrentUser(): AuthUser? {
        return try {
            val user = SupabaseClient.auth.currentUser
            if (user != null) {
                AuthUser(
                    id = user.id,
                    email = user.email ?: "",
                    name = "",
                    phone = ""
                )
            } else null
        } catch (e: Exception) {
            null
        }
    }

    suspend fun deleteAccount(userId: String): Flow<Result<Unit>> = flow {
        try {
            // Delete user from database
            SupabaseClient.db.from("users")
                .delete()
                .eq("id", userId)
            
            // Delete user account
            SupabaseClient.auth.signOut()
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
