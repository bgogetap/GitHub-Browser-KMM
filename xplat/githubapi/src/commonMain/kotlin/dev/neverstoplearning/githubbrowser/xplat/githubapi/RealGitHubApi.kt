package dev.neverstoplearning.githubbrowser.xplat.githubapi

import dev.neverstoplearning.githubbrowser.xplat.githubapi.model.ContributorApiModel
import dev.neverstoplearning.githubbrowser.xplat.githubapi.model.RepoApiModel
import dev.neverstoplearning.githubbrowser.xplat.logging.debugLog
import dev.neverstoplearning.githubbrowser.xplat.logging.errorLog
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

/**
 * [GitHubApi] implementation that makes requests to [gitHubBaseUrl]/... endpoints.
 */
class RealGitHubApi(private val httpClient: HttpClient) : GitHubApi {

    private val baseUrl = "https://api.github.com/"

    override suspend fun getTopRepositories(language: String): List<RepoApiModel>? {
        debugLog("Fetching top repositories")
        return safeResult { httpClient.get(urlString = "${baseUrl}search/repositories?q=language:$language&order=desc&sort=stars&per_page=10&page=1") }
    }

    override suspend fun getRepository(owner: String, repoName: String): RepoApiModel? {
        debugLog("Fetching repository")
        return safeResult { httpClient.get(urlString = "${baseUrl}repos/$owner/$repoName") }
    }

    override suspend fun getContributors(fullRepoName: String): List<ContributorApiModel>? {
        debugLog("Fetching contributors")
        return safeResult { httpClient.get(urlString = "${baseUrl}repos/$fullRepoName/contributors") }
    }

    private suspend inline fun <reified T> safeResult(queryFactory: () -> HttpResponse): T? {
        return try {
            val query = queryFactory()
            if (query.status == HttpStatusCode.OK) {
                query.body()
            } else {
                errorLog(message = "Contributor request unsuccessful.\n${query.status.value} : ${query.body<String>()}")
                null
            }
        } catch (e: Exception) {
            errorLog(e, message = "Contributor request unsuccessful")
            null
        }
    }
}