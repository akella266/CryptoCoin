package ru.akella.cryptocoin.response.exchangedetails

import kotlinx.serialization.Serializable
import ru.akella.cryptocoin.response.Status

@Serializable
data class ExchangeDetailsResponse(
    val data: Map<String, ExchangeDetailsData>,
    val status: Status,
)
