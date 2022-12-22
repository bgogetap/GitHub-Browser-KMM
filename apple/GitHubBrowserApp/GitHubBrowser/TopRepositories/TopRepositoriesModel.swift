//
//  TopRepositoriesModel.swift
//  GitHubBrowser
//
//  Created by Brandon Gogetap on 12/15/22.
//

import Foundation
import GitHubBrowserXplat

class TopRepositoriesModel: ObservableObject {
    
    @Published var viewState: TopRepositoriesViewState = TopRepositoriesViewState.Loading()
    
    private var viewStateUpdatesSubscription: Cancellable?
    
    private let presenter: TopRepositoriesPresenter = TopRepositoriesPresenterFactory().createPresenter()
    
    func start() {
        viewStateUpdatesSubscription = presenter.topRepositoriesViewStateUpdates(
            onEach: {[weak self] viewState in
                self?.viewState = viewState
            },
            onCompletion: { error in
                // TODO: Update view state
                AppLoggerKt.errorLog(error: error, message: "Error listening to view state updates")
            }
        )
    }
    
    func stop() {
        viewStateUpdatesSubscription?.cancel()
        viewStateUpdatesSubscription = nil
    }
}
