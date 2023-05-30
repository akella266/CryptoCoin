package ru.akella.cryptocoin.data.repositories

import co.touchlab.kermit.Logger
import co.touchlab.stately.ensureNeverFrozen
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import ru.akella.cryptocoin.DatabaseHelper
import ru.akella.cryptocoin.data.api.CoinMarketCapApi
import ru.akella.cryptocoin.data.mappers.toCoins
import ru.akella.cryptocoin.db.Breed
import ru.akella.cryptocoin.domain.models.Coin

interface ICryptoCurrencyRepository {

    fun getLatestCoins(): Flow<List<Coin>>
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

    override fun getLatestCoins(): Flow<List<Coin>> = flow {
        val latestListingsResponse = api.fetchLatestListings(1, 100)
        val coins = latestListingsResponse.toCoins()
        emit(coins)
    }

    fun getBreeds(): Flow<List<Breed>> = dbHelper.selectAllItems()

    suspend fun refreshBreedsIfStale() {
        if (isBreedListStale()) {
            refreshBreeds()
        }
    }

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

    suspend fun updateBreedFavorite(breed: Breed) {
        dbHelper.updateFavorite(breed.id, !breed.favorite)
    }

    private fun isBreedListStale(): Boolean {
        val lastDownloadTimeMS = settings.getLong(DB_TIMESTAMP_KEY, 0)
        val oneHourMS = 60 * 60 * 1000
        val stale = lastDownloadTimeMS + oneHourMS < clock.now().toEpochMilliseconds()
        if (!stale) {
            log.i { "Breeds not fetched from network. Recently updated" }
        }
        return stale
    }

}