package ru.akella.cryptocoin.data.mappers

import ru.akella.cryptocoin.data.db.models.CoinDbModel
import ru.akella.cryptocoin.data.response.latestlisting.LatestListingsResponse
import ru.akella.cryptocoin.domain.models.Coin

internal fun LatestListingsResponse.toCoins(): List<Coin> = buildList {
    data.forEach { coin ->
        add(Coin(
            id = coin.id.toString(),
            categoryId = "",
            img = "https://s2.coinmarketcap.com/static/img/coins/64x64/${coin.id}.0.png",
            name = coin.name,
            currentCost = coin.quote.getOrElse("USD") { coin.quote.values.firstOrNull() }?.price ?: 0.0,
            lastChangePercent = coin.quote.getOrElse("USD") { coin.quote.values.firstOrNull() }?.percentChange24H ?: 0.0,
            cap = coin.quote.getOrElse("USD") { coin.quote.values.firstOrNull() }?.marketCap ?: 0.0,
            description = "",
        ))
    }
}

internal fun List<CoinDbModel>.toCoins(): List<Coin> = map {
    Coin(
        id = it.id,
        categoryId = it.categoryId,
        img = it.img,
        name = it.name,
        currentCost = it.currentCost,
        lastChangePercent = it.lastChangePercent,
        cap = it.cap,
        description = it.description,
    )
}

internal fun List<Coin>.toCoinDbModels(): List<CoinDbModel> = map {
    CoinDbModel(
        id = it.id,
        categoryId = it.categoryId,
        img = it.img,
        name = it.name,
        currentCost = it.currentCost,
        lastChangePercent = it.lastChangePercent,
        cap = it.cap,
        description = it.description,
    )
}
