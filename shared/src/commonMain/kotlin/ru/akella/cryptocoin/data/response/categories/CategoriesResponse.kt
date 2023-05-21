package ru.akella.cryptocoin.data.response.categories

import kotlinx.serialization.Serializable
import ru.akella.cryptocoin.data.response.Status

@Serializable
data class CategoriesResponse(
    val data: List<CategoriesData>,
    val status: Status,
)
