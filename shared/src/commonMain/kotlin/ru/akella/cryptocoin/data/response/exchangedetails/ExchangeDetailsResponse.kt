package ru.akella.cryptocoin.data.response.exchangedetails

import kotlinx.serialization.Serializable
import ru.akella.cryptocoin.data.response.Status

@Serializable
data class ExchangeDetailsResponse(
    val data: Map<String, ExchangeDetailsData>,
    val status: Status,
)
