package dev.neverstoplearning.githubbrowser.xplat.repository

import dev.neverstoplearning.githubbrowser.xplat.githubapi.model.ContributorApiModel
import dev.neverstoplearning.githubbrowser.xplat.githubapi.model.RepoApiModel
import dev.neverstoplearning.githubbrowser.xplat.githubapi.model.UserApiModel

// TODO: Move to xplat.database.testutil module

fun createRepoApiModel(
    id: Long = 1L,
    repoName: String = "repo",
    fullRepoName: String = "full_repo",
    description: String? = null,
    ownerId: Long = 2L,
    ownerLogin: String = "user",
    ownerAvatarUrl: String = "https://my.profile/image.jpg",
    stargazersCount: Int = 0,
    forksCount: Int = 0,
    contributorsUrl: String = "https://contributors/$repoName",
    createdDate: String = "2022-12-12T12:00:00",
    updatedDate: String = "2022-12-12T12:00:00"
): RepoApiModel {
    return RepoApiModel(
        id = id,
        name = repoName,
        fullName = fullRepoName,
        description = description,
        owner = createUserApiModel(id = ownerId, login = ownerLogin, avatarUrl = ownerAvatarUrl),
        stargazersCount = stargazersCount,
        forksCount = forksCount,
        contributorsUrl = contributorsUrl,
        createdDate = createdDate,
        updatedDate = updatedDate,
    )
}

fun createUserApiModel(
    id: Long = 2L,
    login: String = "user",
    avatarUrl: String = "https://my.profile/image.jpg"
): UserApiModel {
    return UserApiModel(
        id = id,
        login = login,
        avatarUrl = avatarUrl
    )
}

fun createContributorApiModel(
    id: Long = 2L,
    login: String = "$id",
    avatarUrl: String = "https://my.profile/image$id.jpg",
    contributions: Int = 1
): ContributorApiModel {
    return ContributorApiModel(
        id = id,
        login = login,
        avatarUrl = avatarUrl,
        contributions
    )
}