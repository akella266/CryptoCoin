package ru.akella.cryptocoin.android.latestlistings.model

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import ru.akella.cryptocoin.DispatchersProvider
import ru.akella.cryptocoin.data.api.Sort
import ru.akella.cryptocoin.data.api.SortDirection
import ru.akella.cryptocoin.domain.interactors.GetLatestListsInteractor

class LatestExecutor(
    private val getLatestListingsInteractor: GetLatestListsInteractor,
    private val dispatchersProvider: DispatchersProvider,
) : CoroutineExecutor<LatestIntent, LatestIntent, LatestState, LatestIntent, LatestSideEffect>() {

    override fun executeIntent(intent: LatestIntent, getState: () -> LatestState) {
        when (intent) {
            is LatestIntent.Load -> load()
            is LatestIntent.Refresh -> refresh(getState)
            is LatestIntent.UpdateSort -> updateSort(intent.sortField, getState)
            else -> dispatch(intent)
        }
    }

    private fun load(sort: Sort? = null) {
        getLatestListingsInteractor(sort)
            .flowOn(dispatchersProvider.io())
            .onStart { dispatch(LatestIntent.ShowLoadingState(null, sort)) }
            .onEach { data ->
                dispatch(LatestIntent.ShowLoadedState(data, sort))
            }
            .launchIn(scope)
    }

    private fun refresh(getState: () -> LatestState) {
        val currentSort = getState().run {
            if (this.sortDirection != null && this.activeSortField != null) {
                Sort(sortDirection, activeSortField.value)
            } else {
                null
            }
        }
        load(currentSort)
    }

    private fun updateSort(sortField: SortField, getState: () -> LatestState) {
        val currentState = getState()

        val sort = when (sortField) {
            currentState.activeSortField -> {
                val direction = when (currentState.sortDirection) {
                    SortDirection.ASC -> SortDirection.DESC
                    SortDirection.DESC -> SortDirection.ASC
                    else -> SortDirection.ASC
                }
                Sort(direction = direction, field = sortField.value)
            }
            else -> {
                Sort(direction = SortDirection.ASC, field = sortField.value)
            }
        }
        load(sort)
    }
}
