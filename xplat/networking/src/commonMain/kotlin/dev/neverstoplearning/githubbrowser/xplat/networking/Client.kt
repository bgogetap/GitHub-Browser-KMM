package dev.neverstoplearning.githubbrowser.xplat.networking

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

expect val httpClientEngineFactory: HttpClientEngineFactory<*>

val httpClient: HttpClient
    get() = HttpClient(httpClientEngineFactory) {
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }