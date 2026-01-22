package com.hrithik.fieldagentdirectory.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hrithik.fieldagentdirectory.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM users ORDER BY id")
    fun pagingSource(): PagingSource<Int, UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity>)
}
