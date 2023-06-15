package ru.akella.cryptocoin.android.latestlistings.model

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlin.reflect.KProperty

interface LatestStore : Store<LatestIntent, LatestState, LatestSideEffect>

internal operator fun LatestStore.getValue(thisRef: Any?, property: KProperty<*>) = this

interface LatestStoreFactory {

    fun create(name: String?, initialState: LatestState): LatestStore
}

class LatestStoreFactoryImpl(
    private val storeFactory: StoreFactory,
    private val reducer: LatestReducer,
    private val executor: LatestExecutor,
) : LatestStoreFactory {

    override fun create(name: String?, initialState: LatestState): LatestStore =
        object : LatestStore, Store<LatestIntent, LatestState, LatestSideEffect> by storeFactory.create(
                name = name,
                autoInit = true,
                initialState = initialState,
                bootstrapper = null,
                executorFactory = { executor },
                reducer = reducer,
            ) {}
}
