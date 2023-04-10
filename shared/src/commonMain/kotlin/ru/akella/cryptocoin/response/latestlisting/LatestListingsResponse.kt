package ru.akella.cryptocoin.response.latestlisting

import kotlinx.serialization.Serializable
import ru.akella.cryptocoin.response.Status

@Serializable
data class LatestListingsResponse(
    val data: LatestListingData,
    val status: Status,
)