package ru.akella.cryptocoin.data.response.coininfo

import kotlinx.serialization.Serializable
import ru.akella.cryptocoin.data.response.Status

@Serializable
data class CoinInfoResponse(
    val data: Map<String, CoinInfoData>,
    val status: Status,
)
