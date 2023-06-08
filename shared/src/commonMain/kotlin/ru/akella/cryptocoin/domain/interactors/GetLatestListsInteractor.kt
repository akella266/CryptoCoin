package ru.akella.cryptocoin.domain.interactors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import ru.akella.cryptocoin.DispatchersProvider
import ru.akella.cryptocoin.data.repositories.ICryptoCurrencyRepository
import ru.akella.cryptocoin.domain.asSuccess
import ru.akella.cryptocoin.domain.Result
import ru.akella.cryptocoin.domain.Result.Error
import ru.akella.cryptocoin.domain.Result.Loading
import ru.akella.cryptocoin.domain.models.Coin

class GetLatestListsInteractor(
    private val dispatchersProvider: DispatchersProvider,
    private val repository: ICryptoCurrencyRepository,
) {

    operator fun invoke(): Flow<Result<List<Coin>>> =
        repository.getLatestCoins()
            .map { it.asSuccess() }
            .onStart { emit(Loading()) }
            .catch { emit(Error(it)) }
            .flowOn(dispatchersProvider.io())
}
