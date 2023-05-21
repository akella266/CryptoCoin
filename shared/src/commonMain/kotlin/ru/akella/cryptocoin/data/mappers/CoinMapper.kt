package ru.akella.cryptocoin.data.mappers

import ru.akella.cryptocoin.data.response.latestlisting.LatestListingsResponse
import ru.akella.cryptocoin.domain.models.Coin

internal fun LatestListingsResponse.toCoins(): List<Coin> = buildList {
    data.forEach { coin ->
        add(Coin(
            id = coin.id.toString(),
            categoryId = "",
            img = "https://s2.coinmarketcap.com/static/img/coins/64x64/${coin.id}.png",
            name = coin.name,
            currentCost = coin.quote.getOrElse("USD") { coin.quote.values.firstOrNull() }?.price ?: 0.0,
            lastChangePercent = 0.0,
            cap = 0.0,
            description = "",
        ))
    }
}
