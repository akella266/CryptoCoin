package ru.akella.cryptocoin.response.exchange

import kotlinx.serialization.Serializable
import ru.akella.cryptocoin.response.Status

@Serializable
data class ExchangeResponse(
    val data: ExchangeData,
    val status: Status,
)
