package com.hrithik.fieldagentdirectory.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Keep PostDto only here
data class PostDto(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)

data class PostResponse(val posts: List<PostDto>)

interface PostApi {
    @GET("posts")
    suspend fun getPosts(@Query("userId") userId: Int): PostResponse
}

object PostApiImpl : PostApi {
    private val api: PostApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/") // replace with actual URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(PostApi::class.java)
    }

    override suspend fun getPosts(userId: Int) = api.getPosts(userId)
}
