package com.hrithik.fieldagentdirectory.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface AgentApi {

    @GET("users")
    suspend fun getUsers(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): UserResponseDto
}
