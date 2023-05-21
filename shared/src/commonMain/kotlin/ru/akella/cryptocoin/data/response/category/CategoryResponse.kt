package ru.akella.cryptocoin.data.response.category

import kotlinx.serialization.Serializable
import ru.akella.cryptocoin.data.response.Status

@Serializable
data class CategoryResponse(
    val data: CategoryData,
    val status: Status
)
