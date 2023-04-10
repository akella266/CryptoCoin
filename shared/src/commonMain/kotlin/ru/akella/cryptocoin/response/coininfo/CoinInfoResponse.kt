package ru.akella.cryptocoin.response.coininfo

import kotlinx.serialization.Serializable
import ru.akella.cryptocoin.response.Status

@Serializable
data class CoinInfoResponse(
    val data: Map<String, CoinInfoData>,
    val status: Status,
)
