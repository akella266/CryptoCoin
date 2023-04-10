package ru.akella.cryptocoin.models

import ru.akella.cryptocoin.ktor.CoinMarketCapApi

interface ICryptoCurrencyRepository {

}

class CryptoCurrencyRepository(
    private val api: CoinMarketCapApi
) : ICryptoCurrencyRepository {

}