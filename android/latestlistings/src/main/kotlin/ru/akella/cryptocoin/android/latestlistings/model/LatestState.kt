package ru.akella.cryptocoin.android.latestlistings.model

import ru.akella.cryptocoin.data.api.SortDirection
import ru.akella.cryptocoin.domain.models.Coin

data class LatestState(
    val data: List<Coin>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val activeSortField: SortField? = null,
    val sortDirection: SortDirection? = null
)