//
//  StartupView.swift
//  GitHubBrowser
//
//  Created by Brandon Gogetap on 12/15/22.
//

import Foundation
import SwiftUI

struct StartupView : View {
    
    var body: some View {
        TopRepositoriesView(model: TopRepositoriesModel())
    }
}
