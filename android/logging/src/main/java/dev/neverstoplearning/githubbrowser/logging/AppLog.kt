package dev.neverstoplearning.githubbrowser.logging

import timber.log.Timber

inline fun appLogD(message: String) {
    Timber.d(message)
}

inline fun appLogE(error: Any?, message: String) {
    if (error != null) {
        Timber.e(t = error as? Throwable ?: RuntimeException(error.toString()), message = message)
    } else {
        Timber.e(message = message)
    }
}