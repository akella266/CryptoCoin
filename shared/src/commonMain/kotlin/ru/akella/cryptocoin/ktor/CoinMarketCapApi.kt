package ru.akella.cryptocoin.ktor

import co.touchlab.stately.ensureNeverFrozen
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.koin.core.component.KoinComponent
import ru.akella.cryptocoin.response.categories.CategoriesResponse
import ru.akella.cryptocoin.response.category.CategoryResponse
import ru.akella.cryptocoin.response.coininfo.CoinInfoResponse
import ru.akella.cryptocoin.response.exchange.ExchangeResponse
import ru.akella.cryptocoin.response.exchangedetails.ExchangeDetailsResponse
import ru.akella.cryptocoin.response.latestlisting.LatestListingsResponse
import co.touchlab.kermit.Logger as KermitLogger

class CoinMarketCapApi(
    private val log: KermitLogger,
    private val client: HttpClient,
    private val baseUrl: String = BASE_URL
): KoinComponent {

    init {
        ensureNeverFrozen()
    }

    suspend fun fetchLatestListings(start: Int, limit: Int) =
        client.get("$baseUrl/v1/cryptocurrency/listings/latest?start=$start&limit=$limit").body<LatestListingsResponse>()

    suspend fun fetchCoinDetails(coinId: String) =
        client.get("$baseUrl/v1/cryptocurrency/info?id=$coinId").body<CoinInfoResponse>()

    suspend fun fetchCategories() =
        client.get("$baseUrl/v1/cryptocurrency/categories").body<CategoriesResponse>()

    suspend fun fetchCategory(categoryId: String) =
        client.get("$baseUrl/v1/cryptocurrency/category?id=$categoryId").body<CategoryResponse>()

    suspend fun fetchExchanges(start: Int, limit: Int, cryptoId: String = "") =
        client.get("$baseUrl/v1/exchange/map?start=$start&limit=$limit\"?crypto_id=$cryptoId")
            .body<ExchangeResponse>()

    suspend fun fetchExchangeDetails(exchangeId: String) =
        client.get("$baseUrl/v1/exchange/info?exchange_id=$exchangeId")
            .body<ExchangeDetailsResponse>()
}
