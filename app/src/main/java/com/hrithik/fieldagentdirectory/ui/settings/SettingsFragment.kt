package com.hrithik.fieldagentdirectory.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.work.*
import com.hrithik.fieldagentdirectory.databinding.FragmentSettingsBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Offline-only toggle
        binding.switchOffline.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setOfflineOnly(isChecked)
        }

        // Auto-refresh toggle
        binding.switchAutoRefresh.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setAutoRefresh(isChecked)
        }

        // Collect state
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isOfflineOnly.collectLatest { offline ->
                binding.switchOffline.isChecked = offline
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isAutoRefresh.collectLatest { refresh ->
                binding.switchAutoRefresh.isChecked = refresh
            }
        }

        // Network status and last refresh
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.networkStatus.collectLatest { status ->
                binding.tvNetworkStatus.text = "Network: $status"
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.lastRefresh.collectLatest { ts ->
                binding.tvLastRefresh.text = "Last refresh: $ts"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
