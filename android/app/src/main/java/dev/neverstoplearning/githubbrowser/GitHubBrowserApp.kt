package dev.neverstoplearning.githubbrowser

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dev.neverstoplearning.githubbrowser.feature.home.navigation.homeNavigationRoute
import dev.neverstoplearning.githubbrowser.feature.home.navigation.homeScreen
import dev.neverstoplearning.githubbrowser.feature.repositorydetails.navigation.navigateToRepoDetails
import dev.neverstoplearning.githubbrowser.feature.repositorydetails.navigation.repositoryDetailsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GitHubBrowserApp() {
    Scaffold { padding ->
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = homeNavigationRoute,
            route = "app_route",
            modifier = Modifier.padding(padding)
        ) {
            val onRepoSelected: (repoId: Long) -> Unit = { repoId ->
                navController.navigateToRepoDetails(repoId)
            }
            homeScreen(onRepoSelected)
            repositoryDetailsScreen()
        }
    }
}