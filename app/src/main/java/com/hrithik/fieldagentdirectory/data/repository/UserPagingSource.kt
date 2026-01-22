package com.hrithik.fieldagentdirectory.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hrithik.fieldagentdirectory.data.local.dao.UserDao
import com.hrithik.fieldagentdirectory.data.local.entity.UserEntity
import com.hrithik.fieldagentdirectory.data.mapper.toEntity
import com.hrithik.fieldagentdirectory.data.remote.AgentApi

class UserPagingSource(
    private val api: AgentApi,
    private val dao: UserDao
) : PagingSource<Int, UserEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserEntity> {
        val page = params.key ?: 0
        val pageSize = params.loadSize

        return try {
            // Call API with correct parameters
            val response = api.getUsers(
                limit = pageSize,
                skip = page * pageSize
            )

            // Convert to entity and insert into DB
            val entities = response.users.map { it.toEntity() }
            dao.insertUsers(entities) // Make sure this function exists in UserDao

            LoadResult.Page(
                data = entities,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserEntity>): Int? {
        return state.anchorPosition?.let { anchorPos ->
            val anchorPage = state.closestPageToPosition(anchorPos)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
