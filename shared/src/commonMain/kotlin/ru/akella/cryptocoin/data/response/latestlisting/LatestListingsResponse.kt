package ru.akella.cryptocoin.data.response.latestlisting

import kotlinx.serialization.Serializable
import ru.akella.cryptocoin.data.response.Status

@Serializable
data class LatestListingsResponse(
    val data: List<LatestListingData>,
    val status: Status,
)