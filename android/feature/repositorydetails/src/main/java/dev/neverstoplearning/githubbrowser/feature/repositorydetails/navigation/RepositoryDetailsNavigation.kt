package dev.neverstoplearning.githubbrowser.feature.repositorydetails.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.neverstoplearning.githubbrowser.feature.repositorydetails.RepositoryDetailsScreen
import dev.neverstoplearning.githubbrowser.xplat.feature.repositorydetails.RepositoryDetailsPresenter

private const val baseRoute = "selected_repo"
private const val repoIdArg = "repoId"

fun NavController.navigateToRepoDetails(repoId: Long, navOptions: NavOptions? = null) {
    navigate(buildRoute(repoId), navOptions)
}

fun NavGraphBuilder.RepositoryDetailsScreen() {
    composable(
        route = "$baseRoute/{$repoIdArg}",
        arguments = listOf(navArgument(repoIdArg) { type = NavType.LongType })
    ) { backStackEntry ->
        val repoId =
            checkNotNull(backStackEntry.arguments) { "arguments == null" }.getLong(repoIdArg)
        val presenter = remember { RepositoryDetailsPresenter(repoId = repoId) }
        RepositoryDetailsScreen(presenter)
    }
}

internal fun buildRoute(repoId: Long): String {
    return "$baseRoute/$repoId"
}