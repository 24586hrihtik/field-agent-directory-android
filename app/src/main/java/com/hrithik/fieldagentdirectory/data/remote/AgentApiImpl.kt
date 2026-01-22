package com.hrithik.fieldagentdirectory.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Retrofit implementation singleton
object AgentApiImpl : AgentApi {
    private val api: AgentApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(AgentApi::class.java)
    }

    override suspend fun getUsers(limit: Int, skip: Int) = api.getUsers(limit, skip)
    override suspend fun searchUsers(query: String) = api.searchUsers(query)
    override suspend fun getPosts(userId: Int) = api.getPosts(userId)
}
