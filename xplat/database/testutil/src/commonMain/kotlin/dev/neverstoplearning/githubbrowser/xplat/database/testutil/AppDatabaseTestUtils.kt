package dev.neverstoplearning.githubbrowser.xplat.database.testutil

import com.squareup.sqldelight.db.SqlDriver
import dev.neverstoplearning.githubbrowser.xplat.database.AppDatabase
import dev.neverstoplearning.githubbrowser.xplat.database.GitHubBrowserDatabase

expect fun createTestDriver(): SqlDriver

val databaseTesting: String = "hello"
fun createTestAppDatabase() =
    AppDatabase(gitHubBrowserDatabase = GitHubBrowserDatabase(createTestDriver()))
