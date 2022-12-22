package dev.neverstoplearning.githubbrowser.xplat.database

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

class IosDatabaseDriverFactory : DatabaseDriverFactory {

    override fun createDriver(): SqlDriver {
        return NativeSqliteDriver(GitHubBrowserDatabase.Schema, "appdatabase.db")
    }
}

actual fun getDatabaseDriverFactory(): DatabaseDriverFactory = IosDatabaseDriverFactory()