package com.hrithik.fieldagentdirectory.ui.agent_profile


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrithik.fieldagentdirectory.data.local.entity.PostEntity
import com.hrithik.fieldagentdirectory.data.repository.PostRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AgentProfileViewModel(
    private val postRepository: PostRepository
) : ViewModel() {

    private val _posts = MutableStateFlow<List<PostEntity>>(emptyList())
    val posts: StateFlow<List<PostEntity>> = _posts

    private var fetchJob: Job? = null

    private val handler = CoroutineExceptionHandler { _, throwable ->
        // Handle error gracefully, e.g., show toast/snackbar
    }

    fun loadPosts(userId: Int) {
        fetchJob?.cancel() // cancel previous request if exists

        fetchJob = viewModelScope.launch(handler) {
            val result = postRepository.getPosts(userId)
            _posts.value = result
        }
    }
}
