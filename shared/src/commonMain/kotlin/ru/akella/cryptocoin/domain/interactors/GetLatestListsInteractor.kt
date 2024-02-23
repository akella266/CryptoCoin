package ru.akella.cryptocoin.domain.interactors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import ru.akella.cryptocoin.DispatchersProvider
import ru.akella.cryptocoin.data.api.Sort
import ru.akella.cryptocoin.domain.models.Coin
import ru.akella.cryptocoin.domain.repositories.ICoinsRepository

class GetLatestListsInteractor(
    private val dispatchersProvider: DispatchersProvider,
    private val repository: ICoinsRepository,
)  {

    operator fun invoke(sort: Sort? = null): Flow<List<Coin>> =
        repository.getLatestCoins(sort = sort)
            .flowOn(dispatchersProvider.io())
}