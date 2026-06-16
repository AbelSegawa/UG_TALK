package com.talk.ug.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.talk.ug.data.local.dao.UserDao
import com.talk.ug.data.local.dao.MessageDao
import com.talk.ug.data.local.dao.ChatDao
import com.talk.ug.data.local.entity.UserEntity
import com.talk.ug.data.local.entity.MessageEntity
import com.talk.ug.data.local.entity.ChatEntity

@Database(
    entities = [
        UserEntity::class,
        MessageEntity::class,
        ChatEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao
    abstract fun chatDao(): ChatDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ug_talk_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
