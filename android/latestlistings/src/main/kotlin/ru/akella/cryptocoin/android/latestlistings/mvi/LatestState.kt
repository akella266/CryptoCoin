package ru.akella.cryptocoin.android.latestlistings.mvi

import ru.akella.cryptocoin.domain.models.Coin

data class LatestState(
    val data: List<Coin>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)