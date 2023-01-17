@file:JvmName("AppDatabaseTestUtilsJvm")

package dev.neverstoplearning.githubbrowser.xplat.database.testutil

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import dev.neverstoplearning.githubbrowser.xplat.database.GitHubBrowserDatabase

actual fun createTestDriver(): SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).also {
    GitHubBrowserDatabase.Schema.create(it)
}