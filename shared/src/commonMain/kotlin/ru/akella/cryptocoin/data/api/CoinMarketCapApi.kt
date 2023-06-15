package ru.akella.cryptocoin.data.api

import co.touchlab.stately.ensureNeverFrozen
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.koin.core.component.KoinComponent
import ru.akella.cryptocoin.data.response.categories.CategoriesResponse
import ru.akella.cryptocoin.data.response.category.CategoryResponse
import ru.akella.cryptocoin.data.response.coininfo.CoinInfoResponse
import ru.akella.cryptocoin.data.response.exchange.ExchangeResponse
import ru.akella.cryptocoin.data.response.exchangedetails.ExchangeDetailsResponse
import ru.akella.cryptocoin.data.response.latestlisting.LatestListingsResponse

class CoinMarketCapApi(
    private val client: HttpClient,
    private val baseUrl: String
): KoinComponent {

    init {
        ensureNeverFrozen()
    }

    suspend fun fetchLatestListings(
        start: Int,
        limit: Int,
        sort: Sort? = null,
    ): LatestListingsResponse {
        val sortDirectionParam  = sort?.let {
            "&sort=${it.field}&sort_dir=${it.direction}"
        } ?: ""

        return client.get("$baseUrl/v1/cryptocurrency/listings/latest?start=$start&limit=$limit$sortDirectionParam")
            .body<LatestListingsResponse>()
    }

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
