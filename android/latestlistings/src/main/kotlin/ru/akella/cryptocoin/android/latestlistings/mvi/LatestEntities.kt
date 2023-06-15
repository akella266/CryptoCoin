package ru.akella.cryptocoin.android.latestlistings.mvi

import ru.akella.cryptocoin.domain.models.Coin

sealed class LatestIntent {

    data class ShowLoadingState(val data: List<Coin>?) : LatestIntent()

    data class ShowLoadedState(val data: List<Coin>) : LatestIntent()

    data class ShowErrorState(val errorMessage: String) : LatestIntent()
}

sealed class LatestSideEffect {

}