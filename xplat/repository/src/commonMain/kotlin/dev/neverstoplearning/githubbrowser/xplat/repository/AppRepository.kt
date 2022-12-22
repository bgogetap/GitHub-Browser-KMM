package dev.neverstoplearning.githubbrowser.xplat.repository

import dev.neverstoplearning.githubbrowser.xplat.appmodels.Contributor
import dev.neverstoplearning.githubbrowser.xplat.appmodels.GitHubRepository
import dev.neverstoplearning.githubbrowser.xplat.appmodels.RankedGitHubRepository
import dev.neverstoplearning.githubbrowser.xplat.appmodels.User
import dev.neverstoplearning.githubbrowser.xplat.database.AppDatabase
import dev.neverstoplearning.githubbrowser.xplat.database.GitHubRepositoryDbModel
import dev.neverstoplearning.githubbrowser.xplat.database.GitHubUserDbModel
import dev.neverstoplearning.githubbrowser.xplat.githubapi.GitHubApi
import dev.neverstoplearning.githubbrowser.xplat.githubapi.RealGitHubApi
import dev.neverstoplearning.githubbrowser.xplat.logging.debugLog
import dev.neverstoplearning.githubbrowser.xplat.logging.errorLog
import dev.neverstoplearning.githubbrowser.xplat.networking.httpClient
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.minutes

class AppRepository(
    private val gitHubApi: GitHubApi = RealGitHubApi(httpClient),
    private val appDatabase: AppDatabase = AppDatabase(),
    dispatcher: CoroutineDispatcher
) {

    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)

    init {
        refreshTopRepositories()
        debugLog("AppRepository new instance")
    }

    fun getRepository(repositoryId: Long): Flow<GitHubRepository> {
        return appDatabase.getRepository(repositoryId).mapNotNull {
            GitHubRepository(
                id = it.id,
                name = it.name,
                description = it.description,
                owner = User(
                    id = it.ownerId,
                    username = it.username,
                    avatarUrl = it.avatarUrl
                ),
                starCount = it.starCount,
                forkCount = it.forkCount,
                createdDate = it.repositoryCreatedDate,
                updatedDate = it.repositoryUpdatedDate
            )
        }
    }

    fun getContributorsForRepository(repositoryId: Long): Flow<List<Contributor>> {
        // TODO: Be more intelligent about when to refresh
        refreshContributorsForRepo(repositoryId)
        return appDatabase.getContributorsForRepository(repositoryId).map {
            it.map { dbModel ->
                Contributor(
                    user = User(
                        id = dbModel.userId,
                        username = dbModel.username,
                        avatarUrl = dbModel.avatarUrl
                    ),
                    contributionCount = dbModel.contributionCount
                )
            }
        }
    }

    fun getRankedRepositories(): Flow<List<RankedGitHubRepository>> {
        return appDatabase.getRepositoriesWithRankAndOwner().map {
            it.map { dbItem ->
                RankedGitHubRepository(
                    rank = dbItem.rank, repository = GitHubRepository(
                        id = dbItem.id,
                        name = dbItem.name,
                        description = dbItem.description,
                        owner = User(
                            id = dbItem.userId,
                            username = dbItem.username,
                            avatarUrl = dbItem.avatarUrl
                        ),
                        starCount = dbItem.starCount,
                        forkCount = dbItem.forkCount,
                        createdDate = dbItem.repositoryCreatedDate,
                        updatedDate = dbItem.repositoryUpdatedDate
                    )
                )
            }
        }
    }

    private fun refreshTopRepositories() {
        scope.launch {
            val topRepositories = gitHubApi.getTopRepositories() ?: return@launch
            topRepositories.forEachIndexed { index, repository ->
                val user = GitHubUserDbModel(
                    id = repository.owner.id,
                    username = repository.owner.login,
                    avatarUrl = repository.owner.avatarUrl,
                    timestamp = Clock.System.now().toEpochMilliseconds()
                )

                appDatabase.updateUser(user)

                val dbRepo = GitHubRepositoryDbModel(
                    id = repository.id,
                    name = repository.name,
                    fullName = repository.fullName,
                    description = repository.description,
                    ownerId = repository.owner.id,
                    starCount = repository.stargazersCount,
                    forkCount = repository.forksCount,
                    repositoryCreatedDate = repository.createdDate,
                    repositoryUpdatedDate = repository.updatedDate,
                    timestamp = Clock.System.now().toEpochMilliseconds()
                )

                appDatabase.updateRepository(dbRepo, rank = index + 1)
            }
        }
    }

    private fun refreshContributorsForRepo(repositoryId: Long) {
        scope.launch {
            val repo = appDatabase.getRepository(repositoryId).firstOrNull() ?: return@launch
            val contributors = try {
                gitHubApi.getContributors(repo.fullName) ?: return@launch
            } catch (e: IOException) {
                errorLog(e, "Error fetching contributors")
                return@launch
            }

            val now = Clock.System.now()
            val tenMinutesAgo = now.minus(10.minutes).toEpochMilliseconds()
            debugLog("Updating ${contributors.size} contributors for ${repo.name}")
            contributors.forEach { contributor ->
                if ((appDatabase.getUserLastUpdatedTime(contributor.id) ?: 0) < tenMinutesAgo) {
                    appDatabase.updateUser(
                        GitHubUserDbModel(
                            id = contributor.id,
                            username = contributor.login,
                            avatarUrl = contributor.avatarUrl,
                            timestamp = now.toEpochMilliseconds()
                        )
                    )
                }
                appDatabase.updateContributor(
                    contributionCount = contributor.contributions,
                    contributorId = contributor.id,
                    repoId = repo.id
                )
            }

        }
    }
}
