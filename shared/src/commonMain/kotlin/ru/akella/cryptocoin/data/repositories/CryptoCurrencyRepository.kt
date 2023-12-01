package ru.akella.cryptocoin.data.repositories

import co.touchlab.kermit.Logger
import co.touchlab.stately.ensureNeverFrozen
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import ru.akella.cryptocoin.DatabaseHelper
import ru.akella.cryptocoin.data.api.CoinMarketCapApi
import ru.akella.cryptocoin.data.api.Sort
import ru.akella.cryptocoin.data.mappers.toCoins
import ru.akella.cryptocoin.db.Breed
import ru.akella.cryptocoin.domain.models.Coin

interface ICryptoCurrencyRepository {

    fun getLatestCoins(forceUpdate: Boolean = false, sort: Sort? = null): Flow<List<Coin>>
}

class CryptoCurrencyRepository(
    private val dbHelper: DatabaseHelper,
    private val api: CoinMarketCapApi,
    private val settings: Settings,
    private val log: Logger,
    private val clock: Clock
) : ICryptoCurrencyRepository {

    companion object {
        internal const val DB_TIMESTAMP_KEY = "DbTimestampKey"
    }

    init {
        ensureNeverFrozen()
    }

    override fun getLatestCoins(forceUpdate: Boolean, sort: Sort?): Flow<List<Coin>> = flow {
        val latestListingsResponse = api.fetchLatestListings(1, 100, sort)
        val coins = latestListingsResponse.toCoins()
        emit(coins)
    }

    fun getBreeds(): Flow<List<Breed>> = dbHelper.selectAllItems()


    suspend fun refreshBreeds() {
        // val breedResult = dogApi.getJsonFromApi()
        // log.v { "Breed network result: ${breedResult.status}" }
        // val breedList = breedResult.message.keys.sorted().toList()
        // log.v { "Fetched ${breedList.size} breeds from network" }
        // settings.putLong(DB_TIMESTAMP_KEY, clock.now().toEpochMilliseconds())
        //
        // if (breedList.isNotEmpty()) {
        //     dbHelper.insertBreeds(breedList)
        // }
    }

}