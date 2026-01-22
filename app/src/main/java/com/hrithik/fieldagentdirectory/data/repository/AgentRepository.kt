package com.hrithik.fieldagentdirectory.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hrithik.fieldagentdirectory.data.local.dao.UserDao
import com.hrithik.fieldagentdirectory.data.local.entity.UserEntity
import com.hrithik.fieldagentdirectory.data.mapper.toEntity
import com.hrithik.fieldagentdirectory.data.remote.AgentApi
import kotlinx.coroutines.flow.Flow

class AgentRepository(
    private val api: AgentApi,
    private val dao: UserDao
) {

    fun getAgentsPager(): Flow<PagingData<UserEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { UserPagingSource(api, dao) }
        ).flow
    }

    suspend fun refreshAgents() {
        val response = api.getUsers(limit = 20, skip = 0)
        val entities = response.users.map { it.toEntity() }
        dao.clearUsers()
        dao.insertUsers(entities)
    }
}
