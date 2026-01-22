package com.hrithik.fieldagentdirectory.ui.agent_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hrithik.fieldagentdirectory.data.local.entity.UserEntity
import com.hrithik.fieldagentdirectory.data.repository.AgentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class AgentListViewModel(private val repository: AgentRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // Flow of PagingData filtered by search query
    val agents: Flow<PagingData<UserEntity>> =
        _searchQuery.flatMapLatest { repository.getAgentsPager() }
            .cachedIn(viewModelScope)

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun refreshAgents() {
        viewModelScope.launch {
            repository.refreshAgents()
        }
    }
}