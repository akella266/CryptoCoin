package ru.akella.cryptocoin.android.latestlistings.ui

import co.touchlab.kermit.Logger
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.arkivanov.mvikotlin.extensions.coroutines.states
import kotlinx.coroutines.flow.Flow
import ru.akella.cryptocoin.DispatchersProvider
import ru.akella.cryptocoin.android.core.ResourceProvider
import ru.akella.cryptocoin.android.core.base.BaseScreenModel
import ru.akella.cryptocoin.android.latestlistings.R
import ru.akella.cryptocoin.android.latestlistings.model.LatestIntent
import ru.akella.cryptocoin.android.latestlistings.model.LatestSideEffect
import ru.akella.cryptocoin.android.latestlistings.model.LatestState
import ru.akella.cryptocoin.android.latestlistings.model.LatestStoreFactory
import ru.akella.cryptocoin.android.latestlistings.model.SortField
import ru.akella.cryptocoin.android.latestlistings.model.getValue
import ru.akella.cryptocoin.data.api.Sort
import ru.akella.cryptocoin.domain.interactors.GetLatestListsInteractor

class LatestScreenModel(
    log: Logger,
    storeFactory: LatestStoreFactory,
    dispatchersProvider: DispatchersProvider,
    private val getLatestListingsInteractor: GetLatestListsInteractor,
    private val resourceProvider: ResourceProvider,
) : BaseScreenModel<LatestIntent, LatestState, LatestSideEffect>(log, dispatchersProvider) {

    override val store: Store<LatestIntent, LatestState, LatestSideEffect> by storeFactory.create(
        TAG,
        LatestState()
    )

    override val states: Flow<LatestState> = store.states

    fun loadListings() {
        store.accept(LatestIntent.Load)
    }

    fun refresh() {
        store.accept(LatestIntent.Refresh)
    }

    fun search(query: String) {}

    fun applyMarketCapSort() {
        applySort(SortField.MARKET_CAP)
    }

    fun applyPriceSort() {
        applySort(SortField.PRICE)
    }

    fun applyDailyVolumeSort() {
        applySort(SortField.DAILY)
    }

    private fun applySort(sortField: SortField) {
        store.accept(LatestIntent.UpdateSort(sortField))
    }

    override fun commonErrorOccur() {
        val currentState = store.stateFlow.value
        val sort = if (currentState.sortDirection != null && currentState.activeSortField != null) {
            Sort(currentState.sortDirection, currentState.activeSortField.value)
        } else {
            null
        }
        store.accept(
            LatestIntent.ShowErrorState(
                resourceProvider.getString(R.string.common_error_text),
                sort
            )
        )
    }

    companion object {
        private const val TAG = "LatestScreen"
    }
}