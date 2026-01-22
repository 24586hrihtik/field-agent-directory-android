package com.hrithik.fieldagentdirectory.ui.agent_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hrithik.fieldagentdirectory.data.repository.PostRepository

class AgentProfileViewModelFactory(
    private val repository: PostRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AgentProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AgentProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
