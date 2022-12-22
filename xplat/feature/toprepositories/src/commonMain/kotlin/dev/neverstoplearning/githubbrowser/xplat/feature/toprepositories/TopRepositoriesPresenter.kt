package dev.neverstoplearning.githubbrowser.xplat.feature.toprepositories

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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TopRepositoriesPresenter(
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    scope: CoroutineScope = CoroutineScope(
        SupervisorJob() + dispatcher
    ),
    private val appRepository: AppRepository = AppRepository(dispatcher = dispatcher),
) {

    private val _viewState: MutableStateFlow<TopRepositoriesViewState> =
        MutableStateFlow(TopRepositoriesViewState.Loading)
    val topRepositoriesViewStateUpdates: StateFlow<TopRepositoriesViewState> =
        _viewState.asStateFlow()

    init {
        scope.launch {
            appRepository.getRankedRepositories().collectLatest { rankedRepositories ->
                _viewState.value = TopRepositoriesViewState.Loaded(rankedRepositories)
            }
        }
    }

    /**
     * iOS-friendly way to consume updates
     */
    fun topRepositoriesViewStateUpdates(
        onEach: (TopRepositoriesViewState) -> Unit,
        onCompletion: (Throwable?) -> Unit
    ): Cancellable {
        return topRepositoriesViewStateUpdates.collect(onEach, onCompletion)
    }
}