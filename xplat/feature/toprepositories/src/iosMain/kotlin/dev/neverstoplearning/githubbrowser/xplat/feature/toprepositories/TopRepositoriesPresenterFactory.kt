import dev.neverstoplearning.githubbrowser.xplat.feature.toprepositories.TopRepositoriesPresenter
import dev.neverstoplearning.githubbrowser.xplat.repository.AppRepositoryModule

object TopRepositoriesPresenterFactory {

    fun createPresenter(): TopRepositoriesPresenter {
        return TopRepositoriesPresenter(appRepository = AppRepositoryModule.appRepository)
    }
}
