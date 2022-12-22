package dev.neverstoplearning.githubbrowser.feature.home.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.neverstoplearning.githubbrowser.feature.home.HomeScreen
import dev.neverstoplearning.githubbrowser.xplat.feature.toprepositories.TopRepositoriesPresenter

const val homeNavigationRoute = "home"

//fun NavController.navigateToHome(navOptions: NavOptions? = null) {
//    navigate(homeNavigationRoute)
//}

fun NavGraphBuilder.homeScreen(onRepoSelected: (repoId: Long) -> Unit) {
    composable(route = homeNavigationRoute) {
        val scope = rememberCoroutineScope()
        val topRepositoriesPresenter = remember {
            TopRepositoriesPresenter(scope = scope)
        }
        val viewState = topRepositoriesPresenter.topRepositoriesViewStateUpdates.collectAsState()

        HomeScreen(viewState.value, onRepoSelected)
    }
}