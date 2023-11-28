package ru.akella.cryptocoin.data.db

import org.koin.dsl.module
import ru.akella.cryptocoin.data.db.accessors.CategoryLocalDataSource
import ru.akella.cryptocoin.data.db.accessors.CoinLocalDataSource

internal val dbModule = module {
    factory { CategoryLocalDataSource(get(), get()) }
    factory { CoinLocalDataSource(get(), get()) }
}