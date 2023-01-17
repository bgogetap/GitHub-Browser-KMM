package dev.neverstoplearning.githubbrowser.xplat.repository

import dev.neverstoplearning.githubbrowser.xplat.githubapi.GitHubApi
import dev.neverstoplearning.githubbrowser.xplat.githubapi.model.ContributorApiModel
import dev.neverstoplearning.githubbrowser.xplat.githubapi.model.RepoApiModel
import dev.neverstoplearning.githubbrowser.xplat.githubapi.model.UserApiModel

// TODO: Move to xplat.database.testutil module

/**
 * Fake implementation of [GitHubApi] to be used for unit testing.
 */
class FakeGitHubApi : GitHubApi {

    /**
     * Set the value that should be returned when [getTopRepositories] is invoked.
     */
    var topRepositories: List<RepoApiModel>? = null

    private var ownerAndNameToRepository = mutableMapOf<Pair<String, String>, RepoApiModel>()
    private var repoToContributors = mutableMapOf<String, List<ContributorApiModel>>()

    /**
     * Add a repository that will be returned when [getRepository] is invoked where:
     * [UserApiModel.login] (from [RepoApiModel.owner]) value matches `owner`, and
     * [RepoApiModel.name] value matches `repoName`
     */
    fun addRepository(model: RepoApiModel) {
        ownerAndNameToRepository[model.owner.login to model.name] = model
    }

    /**
     * Add contributors for a repo represented by [fullRepoName]. [contributors] will be returned
     * when [getContributors] is invoked with the matching [fullRepoName].
     */
    fun addContributors(fullRepoName: String, contributors: List<ContributorApiModel>) {
        repoToContributors[fullRepoName] = contributors
    }

    override suspend fun getTopRepositories(language: String): List<RepoApiModel>? {
        return topRepositories
    }

    override suspend fun getRepository(owner: String, repoName: String): RepoApiModel? {
        return ownerAndNameToRepository[owner to repoName]
    }

    override suspend fun getContributors(fullRepoName: String): List<ContributorApiModel>? {
        return repoToContributors[fullRepoName]
    }
}