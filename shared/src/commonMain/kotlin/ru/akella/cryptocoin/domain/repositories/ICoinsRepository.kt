package ru.akella.cryptocoin.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.akella.cryptocoin.data.api.Sort
import ru.akella.cryptocoin.domain.models.Coin

interface ICoinsRepository {

    fun getLatestCoins(forceUpdate: Boolean = false, sort: Sort? = null): Flow<List<Coin>>
}