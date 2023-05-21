package ru.akella.cryptocoin

import kotlinx.coroutines.CoroutineDispatcher

expect class DispatchersProvider {

    fun default(): CoroutineDispatcher

    fun io(): CoroutineDispatcher

    fun main(): CoroutineDispatcher
}
