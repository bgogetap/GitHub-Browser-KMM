package dev.neverstoplearning.githubbrowser.feature.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.neverstoplearning.githubbrowser.theme.GitHubBrowserTheme
import dev.neverstoplearning.githubbrowser.xplat.appmodels.GitHubRepository
import dev.neverstoplearning.githubbrowser.xplat.appmodels.RankedGitHubRepository
import dev.neverstoplearning.githubbrowser.xplat.appmodels.User
import dev.neverstoplearning.githubbrowser.xplat.feature.toprepositories.TopRepositoriesViewState

@Composable
fun HomeScreen(topRepositoriesViewState: TopRepositoriesViewState, onRepoSelected: (repoId: Long) -> Unit) {
    Surface(modifier = Modifier.fillMaxSize()) {
        when (topRepositoriesViewState) {
            is TopRepositoriesViewState.Loading -> Loading()
            is TopRepositoriesViewState.Loaded -> Loaded(viewState = topRepositoriesViewState, onRepoSelected)
        }
    }
}

@Composable
private fun Loading() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
private fun Loaded(viewState: TopRepositoriesViewState.Loaded, onRepoSelected: (repoId: Long) -> Unit) {
    val repositories = viewState.rankedRepositories
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(
            count = repositories.size,
            key = { index -> "${repositories[index].rank}:${repositories[index].repository.id}" },
            itemContent = { index ->
                Box(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface,
                            shape = RoundedCornerShape(size = 4.dp)
                        )
                        .padding(8.dp)
                        .clickable {
                            onRepoSelected(repositories[index].repository.id)
                        }
                ) {
                    RepositoryRow(rankedGitHubRepository = repositories[index])
                }
            }
        )
    }
}

@Composable
private fun RepositoryRow(rankedGitHubRepository: RankedGitHubRepository) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                AsyncImage(
                    model = rankedGitHubRepository.repository.owner.avatarUrl,
                    contentDescription = "Avatar for repository owner ${rankedGitHubRepository.repository.owner.username}",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = rankedGitHubRepository.repository.name,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Text(
                text = "Rank: ${rankedGitHubRepository.rank}",
                style = MaterialTheme.typography.titleSmall
            )
        }
        rankedGitHubRepository.repository.description?.let { description ->
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = description)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            CounterItem(
                iconRes = R.drawable.ic_star_24dp,
                iconContentDescriptionRes = R.string.repo_item_star_icon_content_description,
                count = rankedGitHubRepository.repository.starCount
            )
            Spacer(modifier = Modifier.width(4.dp))
            CounterItem(
                iconRes = R.drawable.ic_fork_24dp,
                iconContentDescriptionRes = R.string.repo_item_fork_count_icon_content_description,
                count = rankedGitHubRepository.repository.forkCount
            )
        }
    }
}

@Composable
private fun CounterItem(
    @DrawableRes iconRes: Int,
    @StringRes iconContentDescriptionRes: Int,
    count: Int
) {
    Row(horizontalArrangement = Arrangement.End) {
        Icon(
            painterResource(id = iconRes),
            contentDescription = stringResource(id = iconContentDescriptionRes)
        )
        Text("$count")
    }
}

@Preview
@Composable
internal fun HomeScreen_Loaded_Preview() {
    GitHubBrowserTheme(darkMode = true) {
        HomeScreen(
            TopRepositoriesViewState.Loaded(
                rankedRepositories = listOf(
                    RankedGitHubRepository(
                        rank = 1,
                        repository = GitHubRepository(
                            id = 1L,
                            name = "Top Repo",
                            description = "Repo that is the top ranked",
                            owner = User(
                                id = 100L,
                                username = "githubuser",
                                avatarUrl = "https://avatars.githubusercontent.com/u/5209417?s=400&u=a16b41d67bce8325a4b8cae4d3e95e8370b35a72&v=4"
                            ),
                            starCount = 5000,
                            forkCount = 267,
                            createdDate = "2012-07-23T13:42:55Z",
                            updatedDate = "2022-07-23T19:12:55Z"
                        )
                    ),
                    RankedGitHubRepository(
                        rank = 2,
                        repository = GitHubRepository(
                            id = 1L,
                            name = "Top Repo #2",
                            description = "Repo that is almost the top ranked",
                            owner = User(
                                id = 100L,
                                username = "githubuser",
                                avatarUrl = "https://avatars.githubusercontent.com/u/5209417?s=400&u=a16b41d67bce8325a4b8cae4d3e95e8370b35a72&v=4"
                            ),
                            starCount = 5000,
                            forkCount = 267,
                            createdDate = "2012-01-23T13:42:55Z",
                            updatedDate = "2022-08-23T19:12:55Z"
                        )
                    )
                )
            ),
            onRepoSelected = {}
        )
    }
}

@Preview
@Composable
internal fun HomeScreen_Loading_Preview() {
    GitHubBrowserTheme(darkMode = true) {
        HomeScreen(TopRepositoriesViewState.Loading, onRepoSelected = {})
    }
}