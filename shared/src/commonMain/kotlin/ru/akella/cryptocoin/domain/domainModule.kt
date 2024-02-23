package ru.akella.cryptocoin.domain

import org.koin.dsl.module
import ru.akella.cryptocoin.data.api.CoinMarketCapApi
import ru.akella.cryptocoin.data.db.accessors.CoinLocalDataSource
import ru.akella.cryptocoin.data.repositories.CoinsRepository
import ru.akella.cryptocoin.domain.interactors.GetLatestListsInteractor
import ru.akella.cryptocoin.domain.repositories.ICoinsRepository

val domainModule = module {
    factory { GetLatestListsInteractor(get(), get()) }
    factory<ICoinsRepository> { CoinsRepository(get<CoinLocalDataSource>(), get<CoinMarketCapApi>()) }
}