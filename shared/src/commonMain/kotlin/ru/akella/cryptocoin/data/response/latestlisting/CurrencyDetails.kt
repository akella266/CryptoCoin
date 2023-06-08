package ru.akella.cryptocoin.data.response.latestlisting

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// todo change double to bigdecimal
@Serializable
data class CurrencyDetails(
    @SerialName("fully_diluted_market_cap")
    val fullyDilutedMarketCap: Double,
    @SerialName("last_updated")
    val lastUpdated: String,
    @SerialName("market_cap")
    val marketCap: Double,
    @SerialName("market_cap_dominance")
    val marketCapDominance: Double,
    @SerialName("percent_change_1h")
    val percentChange1H: Double,
    @SerialName("percent_change_24h")
    val percentChange24H: Double,
    @SerialName("percent_change_7d")
    val percentChange7D: Double,
    val price: Double,
    @SerialName("volume_24h")
    val volume24h: Double,
    @SerialName("volume_change_24h")
    val volumeChange24H: Double
)