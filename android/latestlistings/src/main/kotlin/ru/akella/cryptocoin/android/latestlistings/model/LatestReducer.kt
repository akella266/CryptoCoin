package ru.akella.cryptocoin.android.latestlistings.model

import com.arkivanov.mvikotlin.core.store.Reducer
import ru.akella.cryptocoin.android.latestlistings.model.LatestIntent.ShowErrorState
import ru.akella.cryptocoin.android.latestlistings.model.LatestIntent.ShowLoadedState
import ru.akella.cryptocoin.android.latestlistings.model.LatestIntent.ShowLoadingState

class LatestReducer : Reducer<LatestState, LatestIntent> {

    override fun LatestState.reduce(msg: LatestIntent): LatestState {
        return when (msg) {
            is ShowLoadingState -> copy(
                data = msg.data,
                isLoading = true,
                errorMessage = null,
                activeSortField = msg.sort?.field?.let { SortField.valueOf(it) },
                sortDirection = msg.sort?.direction
            )
            is ShowLoadedState -> copy(
                data = msg.data,
                isLoading = false,
                errorMessage = null,
                activeSortField = msg.sort?.field?.let { SortField.valueOf(it) },
                sortDirection = msg.sort?.direction
            )
            is ShowErrorState -> copy(
                isLoading = false,
                errorMessage = msg.errorMessage,
                activeSortField = msg.sort?.field?.let { SortField.valueOf(it) },
                sortDirection = msg.sort?.direction
            )
            else -> this
        }
    }
}