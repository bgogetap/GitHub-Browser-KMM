//
//  RepositoryDetailsView.swift
//  GitHubBrowser
//
//  Created by Brandon Gogetap on 12/15/22.
//

import Foundation
import SwiftUI
import GitHubBrowserXplat

struct RepositoryDetailsView : View {
    
    @ObservedObject var model: RepositoryDetailsModel
    
    var body: some View {
        let viewState = model.viewState
        VStack {
            switch (viewState) {
            case is RepositoryDetailsViewState.Loading:
                Text("Loading")
            case is RepositoryDetailsViewState.Loaded:
                let repo = (viewState as! RepositoryDetailsViewState.Loaded).repo
                LoadedView(viewState: viewState as! RepositoryDetailsViewState.Loaded)
                    .navigationTitle(repo.name)
            default:
                fatalError("Unhandled viewState: \(viewState)")
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .onAppear {
            model.start()
        }
        .onDisappear {
            model.stop()
        }
    }
}

struct LoadedView : View {
    
    let viewState: RepositoryDetailsViewState.Loaded
    
    var body: some View {
        VStack(alignment: .leading) {
            HStack {
                UserAvatarImage(imageUrl: viewState.repo.owner.avatarUrl)
                Text(viewState.repo.owner.username)
            }
            if let loadedContributors = viewState.repoContributorsState as? RepoContributorsState.Loaded {
                Divider()
                Text("Contributors").font(.title2)
                ContributorsList(contributors: loadedContributors.orderedContributors)
            }
        }.padding()
    }
    
    private func ContributorsList(contributors: [Contributor]) -> some View {
        List(contributors, id: \.user.id) { contributor in
            ContributorRow(contributor: contributor)
        }
    }
    
    private func ContributorRow(contributor: Contributor) -> some View {
        VStack(alignment: .leading) {
            HStack {
                UserAvatarImage(imageUrl: contributor.user.avatarUrl)
                Text(contributor.user.username).font(.body)
            }
            Text("Contributions: \(contributor.contributionCount)")
        }
    }
}

struct RepositoryDetails_Previews: PreviewProvider {
    static var previews: some View {
        LoadedView(
            viewState: RepositoryDetailsViewState.Loaded(
                repo: GitHubRepository(
                id: 1,
                name: "Repository Title",
                description: "Awesome Repository",
                owner: User(
                    id: 2,
                    username: "awesomedev",
                    avatarUrl: ""),
                starCount: 5000,
                forkCount: 880,
                createdDate: "2022-12-12T12:12:12",
                updatedDate: "2022-12-13T12:13:13"),
                repoContributorsState: RepoContributorsState.Loaded(
                    orderedContributors: [Contributor(user: User(id: 2, username: "awesomedev", avatarUrl: ""), contributionCount: 5565)])
            )
        )
    }
}
