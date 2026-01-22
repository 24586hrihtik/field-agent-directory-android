package com.hrithik.fieldagentdirectory.data.repository

import com.hrithik.fieldagentdirectory.data.local.dao.PostDao
import com.hrithik.fieldagentdirectory.data.mapper.toEntity
import com.hrithik.fieldagentdirectory.data.remote.PostApi

class PostRepository(
    private val api: PostApi,
    private val dao: PostDao
) {

    suspend fun getPosts(userId: Int) = dao.getPosts(userId).takeIf { it.isNotEmpty() }
        ?: run {
            val posts = api.getPosts(userId).posts.map { it.toEntity() }
            dao.insertPosts(posts)
            posts
        }
}
