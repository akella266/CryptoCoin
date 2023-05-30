package ru.akella.cryptocoin

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual class DispatchersProvider {

    actual fun default(): CoroutineDispatcher = Dispatchers.Default

    actual fun io(): CoroutineDispatcher = Dispatchers.Main

    actual fun main(): CoroutineDispatcher = Dispatchers.Main
}