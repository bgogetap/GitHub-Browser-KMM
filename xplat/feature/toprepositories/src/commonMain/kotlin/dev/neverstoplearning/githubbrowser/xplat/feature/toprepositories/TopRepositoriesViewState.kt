package dev.neverstoplearning.githubbrowser.xplat.feature.toprepositories

import dev.neverstoplearning.githubbrowser.xplat.appmodels.RankedGitHubRepository

sealed class TopRepositoriesViewState {

    object Loading : TopRepositoriesViewState()
    data class Loaded(val rankedRepositories: List<RankedGitHubRepository>) : TopRepositoriesViewState()
}
