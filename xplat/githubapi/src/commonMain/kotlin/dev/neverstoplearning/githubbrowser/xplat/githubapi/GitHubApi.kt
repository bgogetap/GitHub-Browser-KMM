package dev.neverstoplearning.githubbrowser.xplat.githubapi

import dev.neverstoplearning.githubbrowser.xplat.githubapi.model.ContributorApiModel
import dev.neverstoplearning.githubbrowser.xplat.githubapi.model.RepoApiModel
import dev.neverstoplearning.githubbrowser.xplat.githubapi.model.UserApiModel

interface GitHubApi {

    suspend fun getTopRepositories(language: String = "kotlin"): List<RepoApiModel>?

    suspend fun getRepository(owner: String, repoName: String): RepoApiModel?

    suspend fun getContributors(fullRepoName: String): List<ContributorApiModel>?
}