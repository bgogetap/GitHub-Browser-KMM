package dev.neverstoplearning.githubbrowser.xplat.feature.repositorydetails

import dev.neverstoplearning.githubbrowser.xplat.appmodels.Contributor
import dev.neverstoplearning.githubbrowser.xplat.appmodels.GitHubRepository

sealed class RepositoryDetailsViewState {
    object Loading : RepositoryDetailsViewState()
    data class Loaded(
        val repo: GitHubRepository,
        val repoContributorsState: RepoContributorsState
    ) : RepositoryDetailsViewState()
}

sealed class RepoContributorsState {
    object Loading : RepoContributorsState()
    data class Loaded(val orderedContributors: List<Contributor>) : RepoContributorsState()
}
