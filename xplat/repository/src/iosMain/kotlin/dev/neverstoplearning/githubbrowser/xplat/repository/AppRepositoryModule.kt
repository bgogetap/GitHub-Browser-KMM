package dev.neverstoplearning.githubbrowser.xplat.repository

import dev.neverstoplearning.githubbrowser.xplat.database.DatabaseModule
import kotlinx.coroutines.Dispatchers

/**
 * Module used to provide a single instance of [AppRepository]
 */
object AppRepositoryModule {

    val appRepository: AppRepository by lazy {
        AppRepository(appDatabase = DatabaseModule.database, dispatcher = Dispatchers.Main)
    }
}
