package dev.neverstoplearning.githubbrowser.xplat.database

/**
 * Module used to provide a single instance of [AppDatabase]
 */
object DatabaseModule {

    val database: AppDatabase by lazy {
        AppDatabase()
    }
}