package ru.akella.cryptocoin.android.ui.latest.mvi

import com.arkivanov.mvikotlin.core.store.Reducer
import ru.akella.cryptocoin.android.ui.latest.mvi.LatestIntent.ShowErrorState
import ru.akella.cryptocoin.android.ui.latest.mvi.LatestIntent.ShowLoadedState
import ru.akella.cryptocoin.android.ui.latest.mvi.LatestIntent.ShowLoadingState

class LatestReducer() : Reducer<LatestState, LatestIntent> {

    override fun LatestState.reduce(msg: LatestIntent): LatestState {
        return when (msg) {
            is ShowLoadingState -> LatestState(
                msg.data,
                isLoading = true,
                errorMessage = null,
            )
            is ShowLoadedState -> LatestState(
                msg.data,
                isLoading = false,
                errorMessage = null
            )
            is ShowErrorState -> LatestState(
                isLoading = false,
                errorMessage = msg.errorMessage
            )
            else -> LatestState()
        }
    }
}