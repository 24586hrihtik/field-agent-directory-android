package com.hrithik.fieldagentdirectory.ui.agent_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hrithik.fieldagentdirectory.data.repository.AgentRepository

// Factory to provide repository
class AgentListViewModelFactory(private val repository: AgentRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AgentListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AgentListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
