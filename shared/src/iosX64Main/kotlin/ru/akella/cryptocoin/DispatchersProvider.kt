package ru.akella.cryptocoin

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newFixedThreadPoolContext

actual class DispatchersProvider {

    actual fun default(): CoroutineDispatcher = Dispatchers.Default

    actual fun io(): CoroutineDispatcher {
        return newFixedThreadPoolContext(200, "IO")
    }

    actual fun main(): CoroutineDispatcher = Dispatchers.Main
}