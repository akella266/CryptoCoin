package ru.akella.cryptocoin.domain.models

data class Category(
    val id: String,
    val description: String,
    val tradingVolume: Double,
    val cap: Double,
)
