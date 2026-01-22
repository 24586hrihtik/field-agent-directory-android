package com.hrithik.fieldagentdirectory.ui.agent_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.lifecycleScope
import com.hrithik.fieldagentdirectory.data.local.AppDatabase
import com.hrithik.fieldagentdirectory.data.remote.PostApiImpl
import com.hrithik.fieldagentdirectory.databinding.FragmentAgentProfileBinding
import com.hrithik.fieldagentdirectory.data.repository.PostRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AgentProfileFragment : Fragment() {

    private var _binding: FragmentAgentProfileBinding? = null
    private val binding get() = _binding!!

    // Provide your repository here
    private val postRepository by lazy { PostRepository(PostApiImpl, AppDatabase.getInstance().postDao()) }

    private val viewModel: AgentProfileViewModel by viewModels {
        AgentProfileViewModelFactory(postRepository)
    }

    private lateinit var adapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = PostAdapter()
        binding.postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.postsRecyclerView.adapter = adapter

        val userId = arguments?.getInt("userId") ?: 0

        // Load posts with lazy + offline support
        viewModel.loadPosts(userId)

        // ✅ Collect StateFlow instead of LiveData.observe
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.posts.collectLatest { posts ->
                adapter.submitList(posts)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
