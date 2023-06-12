package ru.akella.cryptocoin.android.ui.base

import cafe.adriel.voyager.core.model.ScreenModel
import co.touchlab.kermit.Logger
import com.arkivanov.mvikotlin.core.store.Store
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.koin.core.component.getScopeName
import ru.akella.cryptocoin.DispatchersProvider
import ru.akella.cryptocoin.domain.BadRequestException
import ru.akella.cryptocoin.domain.ForbiddenException
import ru.akella.cryptocoin.domain.NotFoundException
import ru.akella.cryptocoin.domain.ServerException
import ru.akella.cryptocoin.domain.TooManyRequestsException
import ru.akella.cryptocoin.domain.UnauthorizedException

abstract class BaseScreenModel<Intent: Any, State: Any, SideEffect: Any>(
    private val log: Logger,
    protected val dispatchersProvider: DispatchersProvider,
) : ScreenModel {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { context, throwable ->
        when (throwable) {
            is NotFoundException,
            is BadRequestException,
            is UnauthorizedException,
            is ForbiddenException,
            is TooManyRequestsException,
            is ServerException -> commonErrorOccur()
        }
        log.e(context.getScopeName().value, throwable)
    }

    abstract val store: Store<Intent, State, SideEffect>

    abstract val states: Flow<State>

    protected val modelScope: CoroutineScope = CoroutineScope(
        dispatchersProvider.main() + coroutineExceptionHandler
    )

    open val sideEffects: Flow<SideEffect> = emptyFlow()

    open fun commonErrorOccur() { /*no-op*/ }

    override fun onDispose() {
        modelScope.coroutineContext.cancelChildren()
    }
}