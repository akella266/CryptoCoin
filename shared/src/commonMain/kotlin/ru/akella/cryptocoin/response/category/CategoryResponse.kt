package ru.akella.cryptocoin.response.category

import kotlinx.serialization.Serializable
import ru.akella.cryptocoin.response.Status

@Serializable
data class CategoryResponse(
    val data: CategoryData,
    val status: Status
)
