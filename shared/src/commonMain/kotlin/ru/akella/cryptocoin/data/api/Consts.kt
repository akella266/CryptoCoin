package ru.akella.cryptocoin.data.api

val BASE_URL = "https://pro-api.coinmarketcap.com"

data class Sort(
    val direction: SortDirection,
    val field: String,
)

enum class SortDirection {
    ASC, DESC
}