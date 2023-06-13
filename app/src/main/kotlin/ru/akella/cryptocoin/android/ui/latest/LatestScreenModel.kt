package ru.akella.cryptocoin.android.ui.latest

import co.touchlab.kermit.Logger
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.extensions.coroutines.states
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import ru.akella.cryptocoin.DispatchersProvider
import ru.akella.cryptocoin.android.R
import ru.akella.cryptocoin.android.ui.base.BaseScreenModel
import ru.akella.cryptocoin.android.ui.latest.mvi.LatestIntent
import ru.akella.cryptocoin.android.ui.latest.mvi.LatestSideEffect
import ru.akella.cryptocoin.android.ui.latest.mvi.LatestState
import ru.akella.cryptocoin.android.ui.latest.mvi.LatestStoreFactory
import ru.akella.cryptocoin.android.ui.latest.mvi.getValue
import ru.akella.cryptocoin.domain.interactors.GetLatestListsInteractor

class LatestScreenModel(
    log: Logger,
    storeFactory: LatestStoreFactory,
    dispatchersProvider: DispatchersProvider,
    private val getLatestListingsInteractor: GetLatestListsInteractor,
    private val resourceProvider: ru.akella.cryptocoin.android.core.ResourceProvider,
) : BaseScreenModel<LatestIntent, LatestState, LatestSideEffect>(log, dispatchersProvider) {

    override val store: Store<LatestIntent, LatestState, LatestSideEffect> by storeFactory.create(
        TAG,
        LatestState()
    )

    override val states: Flow<LatestState> = store.states

    fun loadListings() {
        getLatestListingsInteractor()
            .onStart { store.accept(LatestIntent.ShowLoadingState(null)) }
            .onEach { data ->
                store.accept(LatestIntent.ShowLoadedState(data))
            }
            .launchIn(modelScope)
    }

    fun refresh() {
        loadListings()
    }

    fun search(query: String) {
    }

    override fun onDispose() {
        modelScope.coroutineContext.cancelChildren()
    }

    override fun commonErrorOccur() {
        store.accept(LatestIntent.ShowErrorState(resourceProvider.getString(R.string.common_error_text)))
    }

    companion object {
        private const val TAG = "LatestScreen"
    }
}