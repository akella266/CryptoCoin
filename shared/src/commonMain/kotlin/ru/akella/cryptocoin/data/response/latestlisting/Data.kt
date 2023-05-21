package ru.akella.cryptocoin.data.response.latestlisting

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LatestListingData(
    @SerialName("circulating_supply")
    val circulatingSupply: Int,
    @SerialName("cmc_rank")
    val cmcRank: Int,
    @SerialName("date_added")
    val dateAdded: String,
    val id: Int,
    @SerialName("last_updated")
    val lastUpdated: String,
    @SerialName("max_supply")
    val maxSupply: Int,
    val name: String,
    @SerialName("num_market_pairs")
    val numMarketPairs: Int,
    val platform: String,
    val quote: Map<String, CurrencyDetails>,
    val slug: String,
    val symbol: String,
    val tags: List<String>,
    @SerialName("total_supply")
    val totalSupply: Int
)