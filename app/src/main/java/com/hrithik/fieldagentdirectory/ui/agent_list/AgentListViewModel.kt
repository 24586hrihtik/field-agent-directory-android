package com.hrithik.fieldagentdirectory.ui.agent_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.hrithik.fieldagentdirectory.data.repository.AgentRepository

class AgentListViewModel(
    repository: AgentRepository
) : ViewModel() {

    val agents = repository.getAgents()
        .cachedIn(viewModelScope)
}
