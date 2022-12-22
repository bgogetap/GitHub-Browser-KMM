//
//  RepositoryRow.swift
//  GitHubBrowser
//
//  Created by Brandon Gogetap on 12/17/22.
//

import Foundation
import SwiftUI
import GitHubBrowserXplat

struct RepositoryRow : View {
 
    let model: RankedGitHubRepository
    
    var body: some View {
        VStack(alignment: .leading) {
            HStack {
                VStack(alignment: .leading) {
                    UserAvatarImage(imageUrl: model.repository.owner.avatarUrl)
                    Text(model.repository.name).font(.title2)
                }
                Spacer()
                Text("Rank: \(model.rank)").font(.body).frame(alignment: .top)
            }
            if let description = model.repository.description_ {
                Text(description).frame(maxWidth: .infinity, alignment: .leading)
            }
            Spacer().frame(height: 12)
            HStack {
                Image(systemName: "star.fill")
                Text("\(model.repository.starCount)")
                Spacer().frame(width: 6)
                Image(systemName: "tuningfork")
                Text("\(model.repository.forkCount)")
                Spacer()
            }
        }
        .padding()
        .overlay(RoundedRectangle(cornerRadius: 5).stroke(.gray))
    }
}

struct RepositoryRow_Previews: PreviewProvider {
    static var previews: some View {
        RepositoryRow(
            model: RankedGitHubRepository(
                rank: 1,
                repository: GitHubRepository(
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
                    updatedDate: "2022-12-13T12:13:13")
                )
            )
    }
}


