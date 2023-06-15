package ru.akella.cryptocoin.android.latestlistings.model

import ru.akella.cryptocoin.data.api.Sort
import ru.akella.cryptocoin.domain.models.Coin

sealed class LatestIntent {

    object Load : LatestIntent()

    object Refresh : LatestIntent()

    data class UpdateSort(val sortField: SortField) : LatestIntent()

    data class ShowLoadingState(val data: List<Coin>?, val sort: Sort?) : LatestIntent()

    data class ShowLoadedState(val data: List<Coin>, val sort: Sort?) : LatestIntent()

    data class ShowErrorState(val errorMessage: String, val sort: Sort?) : LatestIntent()
}

sealed class LatestSideEffect {

}