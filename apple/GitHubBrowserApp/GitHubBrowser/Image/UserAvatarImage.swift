//
//  UserAvatarImage.swift
//  GitHubBrowser
//
//  Created by Brandon Gogetap on 12/19/22.
//

import Foundation
import SwiftUI

struct UserAvatarImage : View {
    
    let imageUrl: String
    
    var body: some View {
        AsyncImage(
            url: URL(string: imageUrl),
            content: { image in
                image.resizable()
                     .aspectRatio(contentMode: .fit)
                     .frame(width: 24, height: 24)
            },
            placeholder: {
                ProgressView()
            }
        )
    }
}
