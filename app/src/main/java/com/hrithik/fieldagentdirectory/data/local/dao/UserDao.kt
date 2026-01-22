package com.hrithik.fieldagentdirectory.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hrithik.fieldagentdirectory.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM users ORDER BY id LIMIT :limit OFFSET :offset")
    suspend fun getUsers(limit: Int, offset: Int): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Query("DELETE FROM users")
    suspend fun clearUsers()

    suspend fun clearAndInsert(users: List<UserEntity>) {
        clearUsers()
        insertUsers(users)
    }
}
