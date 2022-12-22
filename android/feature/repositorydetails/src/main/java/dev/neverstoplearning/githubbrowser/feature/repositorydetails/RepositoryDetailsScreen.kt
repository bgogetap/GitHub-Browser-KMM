package dev.neverstoplearning.githubbrowser.feature.repositorydetails

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.neverstoplearning.githubbrowser.theme.GitHubBrowserTheme
import dev.neverstoplearning.githubbrowser.xplat.appmodels.Contributor
import dev.neverstoplearning.githubbrowser.xplat.appmodels.GitHubRepository
import dev.neverstoplearning.githubbrowser.xplat.appmodels.User
import dev.neverstoplearning.githubbrowser.xplat.feature.repositorydetails.RepoContributorsState
import dev.neverstoplearning.githubbrowser.xplat.feature.repositorydetails.RepositoryDetailsPresenter
import dev.neverstoplearning.githubbrowser.xplat.feature.repositorydetails.RepositoryDetailsViewState

@Composable
fun RepositoryDetailsScreen(presenter: RepositoryDetailsPresenter) {
    val viewState = presenter.viewStateUpdates.collectAsState()
    RepositoryDetailsScreen(viewState.value)
}

@Composable
fun RepositoryDetailsScreen(RepositoryDetailsViewState: RepositoryDetailsViewState) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        when (RepositoryDetailsViewState) {
            is RepositoryDetailsViewState.Loading -> Loading()
            is RepositoryDetailsViewState.Loaded -> Loaded(RepositoryDetailsViewState)
        }
    }
}

@Composable
private fun Loading() {
    Text("Loading")
}

@Composable
private fun Loaded(viewState: RepositoryDetailsViewState.Loaded) {
    Column {
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = RoundedCornerShape(size = 4.dp)
                )
                .padding(8.dp)
        ) {
            RepositoryDetails(repo = viewState.repo)
        }
        val contributorsState = viewState.repoContributorsState
        if (contributorsState is RepoContributorsState.Loaded) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.contributors_section_label),
                style = MaterialTheme.typography.headlineSmall
            )
            ContributorSection(contributors = contributorsState.orderedContributors)
        }
    }
}

@Composable
private fun RepositoryDetails(repo: GitHubRepository) {
    Column {
        Text(text = repo.name, style = MaterialTheme.typography.headlineMedium)
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = repo.owner.avatarUrl,
                contentDescription = stringResource(
                    id = R.string.repo_owner_avatar_image_content_description, repo.owner.username
                ),
                modifier = Modifier.size(24.dp)
            )
            Text(text = repo.owner.username)
        }
    }
}

@Composable
private fun ContributorSection(contributors: List<Contributor>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(contributors.size, key = { contributors[it].user.username }) { index ->
            ContributorRow(contributor = contributors[index])
        }
    }
}

@Composable
private fun ContributorRow(contributor: Contributor) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.inverseOnSurface,
                shape = RoundedCornerShape(size = 4.dp)
            )
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column {
            Row {
                AsyncImage(
                    model = contributor.user.avatarUrl,
                    contentDescription = stringResource(
                        id = R.string.user_avatar_image_content_description,
                        contributor.user.username
                    ),
                    modifier = Modifier.size(24.dp)
                )
                Text(text = contributor.user.username)
            }
            Row {
                Text(text = stringResource(id = R.string.contribution_count_label))
                Text(text = "${contributor.contributionCount}")
            }
        }
    }
}

@Composable
@Preview
fun RepositoryDetailsScreenLoadedPreview_Dark() {
    GitHubBrowserTheme(darkMode = true) {
        LoadedPreviewContent()
    }
}

@Composable
@Preview
fun RepositoryDetailsScreenLoadedPreview_Light() {
    GitHubBrowserTheme(darkMode = false) {
        LoadedPreviewContent()
    }
}

@Composable
fun LoadedPreviewContent() {
    Surface {
        RepositoryDetailsScreen(
            RepositoryDetailsViewState = RepositoryDetailsViewState.Loaded(
                repo = GitHubRepository(
                    id = 1L,
                    name = "Popular Repository",
                    description = "repo description",
                    owner = User(
                        id = 11L, username = "awesomedev", avatarUrl = ""
                    ),
                    starCount = 3000,
                    forkCount = 1000,
                    createdDate = "2022-12-12T10:00:00",
                    updatedDate = "2022-12-12T10:00:00"
                ), repoContributorsState = RepoContributorsState.Loaded(
                    orderedContributors = listOf(
                        Contributor(
                            user = User(
                                id = 11L, username = "awesomedev", avatarUrl = ""
                            ), contributionCount = 850
                        ), Contributor(
                            user = User(
                                id = 12L, username = "anotherdev", avatarUrl = ""
                            ), contributionCount = 700
                        )
                    )
                )
            )
        )
    }
}