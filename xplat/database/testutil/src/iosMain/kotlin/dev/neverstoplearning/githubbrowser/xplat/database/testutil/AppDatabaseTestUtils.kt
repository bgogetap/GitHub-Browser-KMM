package dev.neverstoplearning.githubbrowser.xplat.database.testutil

import co.touchlab.sqliter.DatabaseConfiguration
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.squareup.sqldelight.drivers.native.wrapConnection
import dev.neverstoplearning.githubbrowser.xplat.database.GitHubBrowserDatabase

actual fun createTestDriver(): SqlDriver {
    val schema = GitHubBrowserDatabase.Schema
    return NativeSqliteDriver(
        configuration = DatabaseConfiguration(
            name = "appdatabase.db",
            version = schema.version,
            create = { connection ->
                wrapConnection(connection) { schema.create(it) }
            },
            upgrade = { connection, oldVersion, newVersion ->
                wrapConnection(connection) { schema.migrate(it, oldVersion, newVersion) }
            },
            inMemory = true
        )
    )
}
