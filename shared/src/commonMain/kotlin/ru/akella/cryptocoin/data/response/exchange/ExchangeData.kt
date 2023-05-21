package ru.akella.cryptocoin.data.response.exchange

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExchangeData(
    val id: Long,
    val name: String,
    val slug: String,
    @SerialName("is_active")
    val isActive: Long,
    val status: String,
    @SerialName("first_historical_data")
    val firstHistoricalData: String,
    @SerialName("last_historical_data")
    val lastHistoricalData: String
)
