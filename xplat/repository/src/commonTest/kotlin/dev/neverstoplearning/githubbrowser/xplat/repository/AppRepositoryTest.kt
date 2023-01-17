package dev.neverstoplearning.githubbrowser.xplat.repository

import dev.neverstoplearning.githubbrowser.xplat.appmodels.GitHubRepository
import dev.neverstoplearning.githubbrowser.xplat.appmodels.RankedGitHubRepository
import dev.neverstoplearning.githubbrowser.xplat.appmodels.User
import dev.neverstoplearning.githubbrowser.xplat.database.testutil.createTestAppDatabase
import dev.neverstoplearning.githubbrowser.xplat.githubapi.model.RepoApiModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AppRepositoryTest {

    private lateinit var appRepository: AppRepository

    private val gitHubApi = FakeGitHubApi()
    private val appDatabase = createTestAppDatabase()
    private val testDispatcher = StandardTestDispatcher()

    @Test
    fun `top repositories are refreshed upon instantiation`() = runBlocking {
        val repoApiModel = createRepoApiModel()
        val topRepositories = listOf(repoApiModel)

        gitHubApi.topRepositories = topRepositories

        initRepository()

        val rankedRepos = appRepository.getRankedRepositories().first()

        val expectedRankedRepo = RankedGitHubRepository(
            rank = 1,
            repository = repoApiModel.toGitHubRepository()
        )
        assertTrue { rankedRepos.size == 1 }
        assertEquals(expectedRankedRepo, rankedRepos.first())
    }

    @Test
    fun `getRepository returns repository from database`() = runBlocking {
        val repoApiModelOne = createRepoApiModel(id = 1L)
        val repoApiModelTwo = createRepoApiModel(id = 2L)

        gitHubApi.topRepositories = listOf(repoApiModelOne, repoApiModelTwo)

        initRepository()

        val fetchedRepoOne = appRepository.getRepository(repoApiModelOne.id).first()
        val expectedFetchedRepoOne = repoApiModelOne.toGitHubRepository()
        assertEquals(expectedFetchedRepoOne, fetchedRepoOne)

        val fetchedRepoTwo = appRepository.getRepository(repoApiModelTwo.id).first()
        val expectedFetchedRepoTwo = repoApiModelTwo.toGitHubRepository()
        assertEquals(expectedFetchedRepoTwo, fetchedRepoTwo)
    }

    private fun RepoApiModel.toGitHubRepository(): GitHubRepository {
        return GitHubRepository(
            id = id,
            name = name,
            description = description,
            owner = User(
                id = owner.id,
                username = owner.login,
                avatarUrl = owner.avatarUrl
            ),
            starCount = stargazersCount,
            forkCount = forksCount,
            createdDate = createdDate,
            updatedDate = updatedDate
        )
    }

    private fun initRepository() {
        appRepository = AppRepository(
            dispatcher = testDispatcher,
            gitHubApi = gitHubApi,
            appDatabase = appDatabase,
        )
        testDispatcher.scheduler.runCurrent()
    }
}