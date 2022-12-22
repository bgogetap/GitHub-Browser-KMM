package dev.neverstoplearning.githubbrowser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import dev.neverstoplearning.githubbrowser.theme.GitHubBrowserTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GitHubBrowserTheme {
                GitHubBrowserApp()
            }
        }
    }
}