package dev.neverstoplearning.githubbrowser.xplat.database

import com.squareup.sqldelight.runtime.coroutines.asFlow
import dev.neverstoplearning.githubbrowser.xplat.logging.errorLog
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

class AppDatabase(
    private val gitHubBrowserDatabase: GitHubBrowserDatabase = AppDatabaseFactory(
        getDatabaseDriverFactory()
    ).getOrCreateDatabase(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    fun getRepository(repositoryId: Long): Flow<RepositoryWithOwnerInfo> {
        return gitHubBrowserDatabase.gitHubRepositoryDbModelQueries.repositoryWithOwnerInfo(
            repositoryId
        )
            .asFlow().map { it.executeAsOne() }
    }

    fun getContributorsForRepository(repositoryId: Long): Flow<List<GetContributorsForRepo>> {
        return gitHubBrowserDatabase.contributorDbModelQueries.getContributorsForRepo(repositoryId)
            .asFlow()
            .map { it.executeAsList() }
    }

    suspend fun getUser(userId: Long): GitHubUserDbModel = withContext(dispatcher) {
        gitHubBrowserDatabase.gitHubUserDbModelQueries.getUserForId(userId).executeAsOne()
    }

    fun getRepositoriesWithRankAndOwner(): Flow<List<RepositoriesWithRankAndOwner>> {
        return gitHubBrowserDatabase.gitHubRepositoryDbModelQueries.repositoriesWithRankAndOwner()
            .asFlow()
            .map { it.executeAsList() }
    }

    suspend fun updateUser(userDbModel: GitHubUserDbModel): Boolean =
        suspendCancellableCoroutine { continuation ->
            try {
                gitHubBrowserDatabase.gitHubUserDbModelQueries.addOrUpdateUser(
                    username = userDbModel.username,
                    avatarUrl = userDbModel.avatarUrl,
                    id = userDbModel.id,
                    timestamp = userDbModel.timestamp
                )
            } catch (e: Exception) {
                errorLog(e, message = "Error updating $userDbModel in database")
                continuation.resume(false)
                return@suspendCancellableCoroutine
            }
            continuation.resume(true)
        }

    /**
     * @return last updated time in millis if exists, otherwise null
     */
    suspend fun getUserLastUpdatedTime(userId: Long): Long? =
        suspendCancellableCoroutine { continuation ->
            try {
                val time = gitHubBrowserDatabase.gitHubUserDbModelQueries.userUpdatedTime(userId)
                    .executeAsOneOrNull()
                continuation.resume(time)
                return@suspendCancellableCoroutine
            } catch (e: Exception) {
                errorLog(e, "Error in query for last update time for user $userId")
            }
            continuation.resume(null)
        }

    suspend fun updateContributor(
        contributorId: Long,
        repoId: Long,
        contributionCount: Int
    ): Boolean =
        suspendCancellableCoroutine { continuation ->
            try {
                gitHubBrowserDatabase.contributorDbModelQueries.addOrUpdateContributorForRepo(
                    contributionCount = contributionCount,
                    repoId = repoId,
                    userId = contributorId
                )
            } catch (e: Exception) {
                errorLog(e, "Error updating contributor in database")
                continuation.resume(false)
                return@suspendCancellableCoroutine
            }

            continuation.resume(true)
        }

    suspend fun updateRepository(
        dbModel: GitHubRepositoryDbModel,
        rank: Int
    ): Boolean = suspendCancellableCoroutine { continuation ->
        gitHubBrowserDatabase.transaction {
            fun continueAndRollback(): Nothing {
                continuation.resume(false)
                rollback()
            }

            try {
                gitHubBrowserDatabase.gitHubRepositoryDbModelQueries.addOrUpdateRepository(
                    dbModel.name,
                    dbModel.fullName,
                    dbModel.description,
                    dbModel.ownerId,
                    dbModel.starCount,
                    dbModel.forkCount,
                    dbModel.repositoryCreatedDate,
                    dbModel.repositoryUpdatedDate,
                    dbModel.timestamp,
                    dbModel.id
                )
            } catch (e: Exception) {
                errorLog(e, message = "Error updating repository $dbModel in transaction.")
                continueAndRollback()
            }

            try {
                gitHubBrowserDatabase.repositoryRankingQueries.addOrUpdateRepositoryRank(
                    repositoryId = dbModel.id,
                    rank
                )
            } catch (e: Exception) {
                errorLog(e, message = "Error updating rank $rank : ${dbModel.id} in transaction.")
                continueAndRollback()
            }

            continuation.resume(true)
        }
    }
}