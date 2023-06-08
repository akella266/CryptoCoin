package ru.akella.cryptocoin.data.response.latestlisting

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LatestListingData(
    @SerialName("circulating_supply")
    val circulatingSupply: Double,
    @SerialName("cmc_rank")
    val cmcRank: Double,
    @SerialName("date_added")
    val dateAdded: String,
    val id: Double,
    @SerialName("last_updated")
    val lastUpdated: String,
    @SerialName("max_supply")
    val maxSupply: Double?,
    val name: String,
    @SerialName("num_market_pairs")
    val numMarketPairs: Double,
    // val platform: String?,
    val quote: Map<String, CurrencyDetails>,
    val slug: String,
    val symbol: String,
    val tags: List<String>,
    @SerialName("total_supply")
    val totalSupply: Double
)