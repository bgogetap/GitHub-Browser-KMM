package dev.neverstoplearning.githubbrowser.xplat.logging

expect inline fun debugLog(message: String)

expect inline fun errorLog(error: Any? = null, message: String)