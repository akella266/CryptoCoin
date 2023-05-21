package ru.akella.cryptocoin.data.response.category

import kotlinx.serialization.SerialName

import kotlinx.serialization.Serializable

@Serializable
data class CategoryData (
    val id: String,
    val name: String,
    val title: String,
    val description: String,
    @SerialName("num_tokens")
    val numTokens: Long,
    @SerialName("avg_price_change")
    val avgPriceChange: Double,
    @SerialName("market_cap")
    val marketCap: Double,
    @SerialName("market_cap_change")
    val marketCapChange: Double,
    val volume: Double,
    @SerialName("volume_change")
    val volumeChange: Double,
    val coins: List<Coin>,
    @SerialName("last_updated")
    val lastUpdated: Long
)

@Serializable
data class Coin (
    val id: Long,
    val name: String,
    val symbol: String,
    val slug: String,
    @SerialName("cmc_rank")
    val cmcRank: Long? = null,
    @SerialName("num_market_pairs")
    val numMarketPairs: Long,
    @SerialName("circulating_supply")
    val circulatingSupply: Long,
    @SerialName("total_supply")
    val totalSupply: Long,
    @SerialName("max_supply")
    val maxSupply: Long,
    @SerialName("last_updated")
    val lastUpdated: String,
    @SerialName("date_added")
    val dateAdded: String,
    val tags: List<String>,
    val platform: String? = null,
    val quote: Map<String, Quote>
)

@Serializable
data class Quote (
    val price: Double,
    @SerialName("volume_24h")
    val volume24H: Long,
    @SerialName("percent_change_1h")
    val percentChange1H: Double,
    @SerialName("percent_change_24h")
    val percentChange24H: Double,
    @SerialName("percent_change_7d")
    val percentChange7D: Double,
    @SerialName("market_cap")
    val marketCap: Long,
    @SerialName("last_updated")
    val lastUpdated: String
)
