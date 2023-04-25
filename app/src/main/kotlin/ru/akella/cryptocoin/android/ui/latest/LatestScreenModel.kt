package ru.akella.cryptocoin.android.ui.latest

import cafe.adriel.voyager.core.model.ScreenModel
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import ru.akella.cryptocoin.android.ui.latest.mvi.LatestIntent
import ru.akella.cryptocoin.android.ui.latest.mvi.LatestSideEffect
import ru.akella.cryptocoin.android.ui.latest.mvi.LatestState
import ru.akella.cryptocoin.android.ui.latest.mvi.LatestStoreFactory
import ru.akella.cryptocoin.android.ui.latest.mvi.getValue

class LatestScreenModel(
    storeFactory: LatestStoreFactory,
) : ScreenModel {

    private val store: Store<LatestIntent, LatestState, LatestSideEffect> by storeFactory.create(
        TAG,
        LatestState("")
    )

    val states = store.states

    val sideEffects = store.labels

    companion object {
        private const val TAG = "LatestScreen"
    }
}