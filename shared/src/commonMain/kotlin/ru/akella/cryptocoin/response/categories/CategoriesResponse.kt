package ru.akella.cryptocoin.response.categories

import kotlinx.serialization.Serializable
import ru.akella.cryptocoin.response.Status

@Serializable
data class CategoriesResponse(
    val data: List<CategoriesData>,
    val status: Status,
)
