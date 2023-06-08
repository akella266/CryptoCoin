package ru.akella.cryptocoin.android.ui.latest

import cafe.adriel.voyager.core.model.ScreenModel
import co.touchlab.kermit.Logger
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.component.getScopeName
import ru.akella.cryptocoin.DispatchersProvider
import ru.akella.cryptocoin.android.ui.latest.mvi.LatestIntent
import ru.akella.cryptocoin.android.ui.latest.mvi.LatestSideEffect
import ru.akella.cryptocoin.android.ui.latest.mvi.LatestState
import ru.akella.cryptocoin.android.ui.latest.mvi.LatestStoreFactory
import ru.akella.cryptocoin.android.ui.latest.mvi.getValue
import ru.akella.cryptocoin.android.util.ErrorMapper
import ru.akella.cryptocoin.domain.Result.Error
import ru.akella.cryptocoin.domain.Result.Loading
import ru.akella.cryptocoin.domain.Result.Success
import ru.akella.cryptocoin.domain.interactors.GetLatestListsInteractor

class LatestScreenModel(
    storeFactory: LatestStoreFactory,
    dispatchersProvider: DispatchersProvider,
    private val log: Logger,
    private val getLatestListingsInteractor: GetLatestListsInteractor,
    private val errorMapper: ErrorMapper,
) : ScreenModel {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { context, throwable ->
        log.e(context.getScopeName().value, throwable)
    }

    private val modelScope: CoroutineScope = CoroutineScope(
        dispatchersProvider.main() + coroutineExceptionHandler
    )

    private val store: Store<LatestIntent, LatestState, LatestSideEffect> by storeFactory.create(
        TAG,
        LatestState()
    )

    val states = store.states

    val sideEffects = store.labels

    fun loadListings() {
        getLatestListingsInteractor()
            .onEach { state ->
                val intent =
                    when (state) {
                        is Loading -> LatestIntent.ShowLoadingState(state.data)
                        is Success -> LatestIntent.ShowLoadedState(state.data)
                        is Error -> {
                            log.e("latestListingsInteractor", state.e)
                            LatestIntent.ShowErrorState(errorMapper.mapError(state.e))
                        }
                    }
                store.accept(intent)
            }
            .launchIn(modelScope)
    }

    override fun onDispose() {
        modelScope.coroutineContext.cancelChildren()
    }

    companion object {
        private const val TAG = "LatestScreen"
    }
}