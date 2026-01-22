package com.hrithik.fieldagentdirectory

import android.app.Application
import com.hrithik.fieldagentdirectory.data.local.AppDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        // Initialize DB here if needed
        AppDatabase.getInstance(this)
    }

    companion object {
        lateinit var instance: App
            private set
    }
}
