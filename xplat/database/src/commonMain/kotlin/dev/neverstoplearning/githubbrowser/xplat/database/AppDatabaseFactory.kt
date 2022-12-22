package dev.neverstoplearning.githubbrowser.xplat.database

import com.squareup.sqldelight.db.SqlDriver

interface DatabaseDriverFactory {

    fun createDriver(): SqlDriver
}

expect fun getDatabaseDriverFactory(): DatabaseDriverFactory

internal class AppDatabaseFactory(private val driverFactory: DatabaseDriverFactory = getDatabaseDriverFactory()) {

    companion object {

        private var instance: GitHubBrowserDatabase? = null
    }

    fun getOrCreateDatabase(): GitHubBrowserDatabase {
        // TODO: Make thread safe
        if (instance == null) {
            instance = GitHubBrowserDatabase(driver = driverFactory.createDriver())
        }
        return checkNotNull(instance)
    }
}