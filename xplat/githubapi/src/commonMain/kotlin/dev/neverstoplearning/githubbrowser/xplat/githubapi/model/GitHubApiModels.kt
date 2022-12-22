package dev.neverstoplearning.githubbrowser.xplat.githubapi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserApiModel(
    val id: Long,
    val login: String,
    @SerialName("avatar_url") val avatarUrl: String
)

@Serializable
data class ContributorApiModel(
    val id: Long,
    val login: String,
    @SerialName("avatar_url") val avatarUrl: String,
    val contributions: Int
)

@Serializable
data class RepoApiModel(
    val id: Long,
    val name: String,
    @SerialName("full_name") val fullName: String,
    val description: String?,
    val owner: UserApiModel,
    @SerialName("stargazers_count") val stargazersCount: Int,
    @SerialName("forks") val forksCount: Int,
    @SerialName("contributors_url") val contributorsUrl: String,
    @SerialName("created_at") val createdDate: String,
    @SerialName("updated_at") val updatedDate: String
)

@Serializable
data class TopRepositoriesQuery(val items: List<RepoApiModel>)