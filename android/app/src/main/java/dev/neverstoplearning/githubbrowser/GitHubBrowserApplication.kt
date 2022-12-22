package dev.neverstoplearning.githubbrowser

import android.app.Application
import dev.neverstoplearning.githubbrowser.xplat.database.AndroidDatabaseDriverFactory
import timber.log.Timber

class GitHubBrowserApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        // TODO: Replace with "AppInitializer" multibindings?
        AndroidDatabaseDriverFactory.applicationContext = this
    }
}