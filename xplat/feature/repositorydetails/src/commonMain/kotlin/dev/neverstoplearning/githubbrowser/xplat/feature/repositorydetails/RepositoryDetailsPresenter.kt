package dev.neverstoplearning.githubbrowser.xplat.feature.repositorydetails

import dev.neverstoplearning.githubbrowser.xplat.coroutines.Cancellable
import dev.neverstoplearning.githubbrowser.xplat.coroutines.collect
import dev.neverstoplearning.githubbrowser.xplat.repository.AppRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class RepositoryDetailsPresenter(
    private val repoId: Long,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    scope: CoroutineScope = CoroutineScope(
        SupervisorJob() + dispatcher
    ),
    private val appRepository: AppRepository = AppRepository(dispatcher = dispatcher),
) {

    private val _viewState: MutableStateFlow<RepositoryDetailsViewState> =
        MutableStateFlow(RepositoryDetailsViewState.Loading)
    val viewStateUpdates: StateFlow<RepositoryDetailsViewState> = _viewState.asStateFlow()

    init {
        scope.launch {
            combine(
                appRepository.getRepository(repoId),
                appRepository.getContributorsForRepository(repoId)
            ) { repo, contributors -> repo to contributors }
                .collect { repoContributorsPair ->
                    val (repo, contributors) = repoContributorsPair
                    _viewState.value = RepositoryDetailsViewState.Loaded(
                        repo = repo,
                        repoContributorsState = RepoContributorsState.Loaded(contributors)
                    )
                }
        }
    }

    /**
     * iOS-friendly way to consume updates
     */
    fun viewStateUpdates(
        onEach: (RepositoryDetailsViewState) -> Unit,
        onCompletion: (Throwable?) -> Unit
    ): Cancellable {
        return viewStateUpdates.collect(onEach, onCompletion)
    }
}