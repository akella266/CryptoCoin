package ru.akella.cryptocoin.data.db.models

data class CategoryDbModel(
    val id: String,
    val description: String,
    val tradingVolume: Double,
    val cap: Double,
)
