package dev.neverstoplearning.githubbrowser.xplat.appmodels

data class User(val id: Long, val username: String, val avatarUrl: String)

data class Contributor(
    val user: User,
    val contributionCount: Int,
)

data class GitHubRepository(
    val id: Long,
    val name: String,
    val description: String?,
    val owner: User,
    val starCount: Int,
    val forkCount: Int,
    val createdDate: String,
    val updatedDate: String
)

data class RankedGitHubRepository(
    val rank: Int,
    val repository: GitHubRepository
)
