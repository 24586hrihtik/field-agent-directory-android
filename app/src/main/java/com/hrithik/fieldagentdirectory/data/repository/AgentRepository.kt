package com.hrithik.fieldagentdirectory.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hrithik.fieldagentdirectory.data.local.dao.UserDao
import com.hrithik.fieldagentdirectory.data.remote.AgentApi
import kotlinx.coroutines.flow.Flow

class AgentRepository(
    private val api: AgentApi,
    private val dao: UserDao
) {

    fun getAgents(): Flow<PagingData<Int>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UserPagingSource(api, dao) }
        ).flow
    }
}
