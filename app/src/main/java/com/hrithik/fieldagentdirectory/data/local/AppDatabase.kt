package com.hrithik.fieldagentdirectory.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hrithik.fieldagentdirectory.data.local.dao.UserDao
import com.hrithik.fieldagentdirectory.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
