//
//  TopRepositoriesView.swift
//  GitHubBrowser
//
//  Created by Brandon Gogetap on 12/15/22.
//

import Foundation
import SwiftUI
import GitHubBrowserXplat

struct TopRepositoriesView : View {
    
    @ObservedObject var model: TopRepositoriesModel
    
    var body: some View {
        let viewState = model.viewState
        NavigationView {
            switch viewState {
            case is TopRepositoriesViewState.Loading:
                Text("Loading")
            case is TopRepositoriesViewState.Loaded:
                List((viewState as! TopRepositoriesViewState.Loaded).rankedRepositories, id: \.repository.id) { rankedRepository in
                    NavigationLink(destination: LazyView(contentFactory: {
                        RepositoryDetailsView(model: RepositoryDetailsModel(repositoryId: rankedRepository.repository.id))})) {
                        RepositoryRow(model: rankedRepository)
                    }
                }
                .navigationTitle("GitHub Browser")
            default:
                fatalError("Unhandled viewState: \(viewState)")
            }
        }
        .onAppear {
            model.start()
        }.onDisappear {
            model.stop()
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}

struct LazyView<Content: View> : View {
    
    let contentFactory: () -> Content
    
    var body: some View {
        contentFactory()
    }
}
