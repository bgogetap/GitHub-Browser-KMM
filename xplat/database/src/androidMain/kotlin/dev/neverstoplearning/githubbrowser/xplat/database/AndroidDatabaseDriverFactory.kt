package dev.neverstoplearning.githubbrowser.xplat.database

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

class AndroidDatabaseDriverFactory : DatabaseDriverFactory {

    companion object {
        var applicationContext: Context? = null
    }

    override fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            GitHubBrowserDatabase.Schema,
            checkNotNull(applicationContext) { "Database cannot be initialized before Application Context is set." },
            "appdatabase.db"
        )
    }
}

actual fun getDatabaseDriverFactory(): DatabaseDriverFactory = AndroidDatabaseDriverFactory()