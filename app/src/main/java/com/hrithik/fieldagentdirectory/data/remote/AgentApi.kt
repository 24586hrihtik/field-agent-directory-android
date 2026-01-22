package com.hrithik.fieldagentdirectory.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Keep UserDto only here
data class UserDto(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String
)

data class UserResponse(val users: List<UserDto>)

interface AgentApi {
    @GET("users")
    suspend fun getUsers(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): UserResponse

    @GET("users/search")
    suspend fun searchUsers(
        @Query("q") query: String
    ): UserResponse

    @GET("posts/user/{id}")
    suspend fun getPosts(@Path("id") userId: Int): PostResponse
}
