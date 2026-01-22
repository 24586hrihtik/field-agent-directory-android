package com.hrithik.fieldagentdirectory.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.hrithik.fieldagentdirectory.App
import com.hrithik.fieldagentdirectory.data.worker.RefreshWorker
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class SettingsViewModel(
) : ViewModel() {

    private val prefs: SharedPreferences =
        App.instance.getSharedPreferences("settings", Context.MODE_PRIVATE)

    private val _isOfflineOnly = MutableStateFlow(prefs.getBoolean("offline_only", false))
    val isOfflineOnly: StateFlow<Boolean> = _isOfflineOnly

    private val _isAutoRefresh = MutableStateFlow(prefs.getBoolean("auto_refresh", true))
    val isAutoRefresh: StateFlow<Boolean> = _isAutoRefresh

    private val _networkStatus = MutableStateFlow("Unknown")
    val networkStatus: StateFlow<String> = _networkStatus

    private val _lastRefresh = MutableStateFlow(prefs.getString("last_refresh", "Never")!!)
    val lastRefresh: StateFlow<String> = _lastRefresh

    fun setOfflineOnly(enabled: Boolean) {
        prefs.edit().putBoolean("offline_only", enabled).apply()
        _isOfflineOnly.value = enabled
    }

    fun setAutoRefresh(enabled: Boolean) {
        prefs.edit().putBoolean("auto_refresh", enabled).apply()
        _isAutoRefresh.value = enabled
        scheduleAutoRefresh(enabled)
    }

    private fun scheduleAutoRefresh(enabled: Boolean) {
        if (enabled) {
            // Schedule WorkManager periodic refresh
            val workRequest = PeriodicWorkRequestBuilder<RefreshWorker>(15, TimeUnit.MINUTES)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                ).build()
            WorkManager.getInstance(App.instance).enqueueUniquePeriodicWork(
                "auto_refresh",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
        } else {
            WorkManager.getInstance(App.instance).cancelUniqueWork("auto_refresh")
        }
    }

    fun updateNetworkStatus(context: Context) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nc = cm.getNetworkCapabilities(cm.activeNetwork)
        _networkStatus.value = if (nc != null &&
            (nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
        ) "Online" else "Offline"
    }

    fun updateLastRefresh() {
        val ts = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(Date())
        prefs.edit().putString("last_refresh", ts).apply()
        _lastRefresh.value = ts
    }
}
