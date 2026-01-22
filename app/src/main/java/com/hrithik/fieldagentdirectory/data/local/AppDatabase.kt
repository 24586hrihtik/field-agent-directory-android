package com.hrithik.fieldagentdirectory.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hrithik.fieldagentdirectory.App
import com.hrithik.fieldagentdirectory.data.local.dao.PostDao
import com.hrithik.fieldagentdirectory.data.local.dao.UserDao
import com.hrithik.fieldagentdirectory.data.local.entity.PostEntity
import com.hrithik.fieldagentdirectory.data.local.entity.UserEntity

@Database(entities = [UserEntity::class, PostEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context = App.instance): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
