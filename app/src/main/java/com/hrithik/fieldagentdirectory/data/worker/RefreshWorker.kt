package com.hrithik.fieldagentdirectory.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hrithik.fieldagentdirectory.ui.settings.SettingsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RefreshWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            // Fetch latest agents/posts if auto-refresh enabled
            // Use your AgentRepository / PostRepository here
            // Example:
            // val repository = AgentRepository(...)
            // repository.refreshAll()

            // Update last refresh timestamp
            val settingsViewModel = SettingsViewModel()
            settingsViewModel.updateLastRefresh()

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
