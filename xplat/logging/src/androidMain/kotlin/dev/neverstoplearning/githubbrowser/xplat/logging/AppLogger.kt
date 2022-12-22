package dev.neverstoplearning.githubbrowser.xplat.logging

import dev.neverstoplearning.githubbrowser.logging.appLogD
import dev.neverstoplearning.githubbrowser.logging.appLogE

actual inline fun debugLog(message: String) {
    appLogD(message)
}

actual inline fun errorLog(error: Any?, message: String) {
    appLogE(error, message)
}