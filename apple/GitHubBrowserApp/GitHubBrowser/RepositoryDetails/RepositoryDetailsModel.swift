//
//  RepositoryDetailsModel.swift
//  GitHubBrowser
//
//  Created by Brandon Gogetap on 12/15/22.
//

import Foundation
import GitHubBrowserXplat

class RepositoryDetailsModel: ObservableObject {

    @Published var viewState: RepositoryDetailsViewState = RepositoryDetailsViewState.Loading()

    private let repositoryId: Int64
    private let presenter: RepositoryDetailsPresenter

    private var repositoryUpdatesSubscription: Cancellable? = nil

    init(repositoryId: Int64) {
        self.repositoryId = repositoryId

        presenter = RepositoryDetailsPresenterFactory().createPresenter(repoId: repositoryId)
    }

    func start() {
        repositoryUpdatesSubscription = presenter.viewStateUpdates(
                onEach: { [weak self] viewState in
                    self?.viewState = viewState
                },
                onCompletion: { error in
                    // TODO: Update view state
                    AppLoggerKt.errorLog(error: error, message: "Error in view state updates subscription")
                })
    }

    func stop() {

    }
}
