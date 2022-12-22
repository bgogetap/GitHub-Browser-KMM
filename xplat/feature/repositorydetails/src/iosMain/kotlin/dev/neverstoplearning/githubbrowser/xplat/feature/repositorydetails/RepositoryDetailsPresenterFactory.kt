package dev.neverstoplearning.githubbrowser.xplat.feature.repositorydetails

import dev.neverstoplearning.githubbrowser.xplat.repository.AppRepositoryModule

object RepositoryDetailsPresenterFactory {

    fun createPresenter(repoId: Long): RepositoryDetailsPresenter {
        return RepositoryDetailsPresenter(repoId = repoId, appRepository = AppRepositoryModule.appRepository)
    }
}