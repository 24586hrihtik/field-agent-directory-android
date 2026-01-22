package com.hrithik.fieldagentdirectory.ui.agent_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hrithik.fieldagentdirectory.data.local.AppDatabase
import com.hrithik.fieldagentdirectory.data.repository.AgentRepository
import com.hrithik.fieldagentdirectory.databinding.FragmentAgentListBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AgentListFragment : Fragment() {

    private var _binding: FragmentAgentListBinding? = null
    private val binding get() = _binding!!

    private val repository by lazy {
        AgentRepository(com.hrithik.fieldagentdirectory.data.remote.AgentApiImpl, AppDatabase.getInstance().userDao())
    }

    private val viewModel: AgentListViewModel by viewModels {
        AgentListViewModelFactory(repository)
    }

    private lateinit var adapter: AgentAdapter
    private var searchJob: Job? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentAgentListBinding.inflate(inflater, container, false).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        adapter = AgentAdapter { user ->
            val action = AgentListFragmentDirections.actionAgentListToAgentProfile(user.id)
            findNavController().navigate(action)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Search with debounce
        binding.etSearch.addTextChangedListener { editable ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(300)
                viewModel.setSearchQuery(editable.toString())
            }
        }

        // Collect paging data
        lifecycleScope.launch {
            viewModel.agents.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
