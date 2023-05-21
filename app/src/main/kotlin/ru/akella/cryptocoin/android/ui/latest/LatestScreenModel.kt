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
import ru.akella.cryptocoin.domain.interactors.GetLatestListsInteractor
import kotlin.coroutines.CoroutineContext

class LatestScreenModel(
    storeFactory: LatestStoreFactory,
    private val log: Logger,
    private val dispatchersProvider: DispatchersProvider,
    private val getLatestListingsInteractor: GetLatestListsInteractor
) : ScreenModel {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { context, throwable ->
        log.e(context.getScopeName().value, throwable)
    }

    private val modelScope: CoroutineScope = CoroutineScope(
        dispatchersProvider.main() + coroutineExceptionHandler
    )

    private val store: Store<LatestIntent, LatestState, LatestSideEffect> by storeFactory.create(
        TAG,
        LatestState("")
    )

    val states = store.states

    val sideEffects = store.labels

    fun loadListings() {
        getLatestListingsInteractor()
            .onEach {  }
            .launchIn(modelScope)
    }

    override fun onDispose() {
        modelScope.coroutineContext.cancelChildren()
    }

    companion object {
        private const val TAG = "LatestScreen"
    }
}