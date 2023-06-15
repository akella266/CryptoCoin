package ru.akella.cryptocoin.android.latestlistings.mvi

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlin.reflect.KProperty

interface LatestStore : Store<LatestIntent, LatestState, LatestSideEffect>

internal operator fun LatestStore.getValue(thisRef: Any?, property: KProperty<*>) = this

interface LatestStoreFactory {

    fun create(name: String?, initialState: LatestState): LatestStore
}

class LatestStoreFactoryImpl(
    private val storeFactory: StoreFactory,
    private val reducer: LatestReducer,
) : LatestStoreFactory {

    override fun create(name: String?, initialState: LatestState): LatestStore =
        object : LatestStore, Store<LatestIntent, LatestState, LatestSideEffect> by storeFactory.create(
                name = name,
                autoInit = true,
                initialState = initialState,
                bootstrapper = null,
                executorFactory = { LatestExecutor() },
                reducer = reducer,
            ) {}
}

private class LatestExecutor() : CoroutineExecutor<LatestIntent, LatestIntent, LatestState, LatestIntent, LatestSideEffect>() {

    override fun executeIntent(intent: LatestIntent, getState: () -> LatestState) {
        dispatch(intent)
    }
}
