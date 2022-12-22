package dev.neverstoplearning.githubbrowser.xplat.logging

import platform.Foundation.NSLog

actual inline fun debugLog(message: String) {
    NSLog(message)
}

actual inline fun errorLog(error: Any?, message: String) {
    NSLog("Error: ${error?.toString()}\nMessage: $message")
}
