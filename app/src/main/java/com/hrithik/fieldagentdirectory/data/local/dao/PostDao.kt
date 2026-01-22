package com.hrithik.fieldagentdirectory.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hrithik.fieldagentdirectory.data.local.entity.PostEntity

@Dao
interface PostDao {

    @Query("SELECT * FROM posts WHERE userId = :userId")
    suspend fun getPosts(userId: Int): List<PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)
}
