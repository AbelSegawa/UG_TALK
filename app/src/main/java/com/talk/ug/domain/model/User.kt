package com.talk.ug.domain.model

data class User(
    val id: String,
    val email: String,
    val name: String,
    val phone: String,
    val bio: String = "",
    val profilePicUrl: String = "",
    val isOnline: Boolean = false,
    val lastSeen: Long = 0,
    val createdAt: Long = System.currentTimeMillis()
)
