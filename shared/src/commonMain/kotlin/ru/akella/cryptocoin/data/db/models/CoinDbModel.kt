package ru.akella.cryptocoin.data.db.models

data class CoinDbModel(
    val id: String,
    val categoryId: String,
    val img: String?,
    val name: String,
    val currentCost: Double,
    val lastChangePercent: Double,
    val cap: Double,
    val description: String,
)
