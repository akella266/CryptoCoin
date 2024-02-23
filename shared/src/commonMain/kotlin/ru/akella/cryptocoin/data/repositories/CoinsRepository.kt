package ru.akella.cryptocoin.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import ru.akella.cryptocoin.data.api.CoinMarketCapApi
import ru.akella.cryptocoin.data.api.Sort
import ru.akella.cryptocoin.data.db.accessors.CoinLocalDataSource
import ru.akella.cryptocoin.data.mappers.toCoinDbModels
import ru.akella.cryptocoin.data.mappers.toCoins
import ru.akella.cryptocoin.domain.models.Coin
import ru.akella.cryptocoin.domain.repositories.ICoinsRepository

internal class CoinsRepository(
    private val coinDb: CoinLocalDataSource,
    private val api: CoinMarketCapApi,
) : ICoinsRepository {

    override fun getLatestCoins(forceUpdate: Boolean, sort: Sort?): Flow<List<Coin>> = flow {
        val localData = coinDb.getAll().firstOrNull()

        localData?.let {
            emit(it.toCoins())
        }

        if (localData.isNullOrEmpty() || forceUpdate) {
            val latestListingsResponse = api.fetchLatestListings(1, 100, sort)

            if (forceUpdate) {
                coinDb.deleteAll()
            }

            val coins = latestListingsResponse.toCoins()
            emit(coins)
            coins.toCoinDbModels().forEach {
                coinDb.put("", it)
            }
        }
    }
}