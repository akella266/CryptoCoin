package ru.akella.cryptocoin.data.response.exchange

import kotlinx.serialization.Serializable
import ru.akella.cryptocoin.data.response.Status

@Serializable
data class ExchangeResponse(
    val data: ExchangeData,
    val status: Status,
)
