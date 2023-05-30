package ru.akella.cryptocoin.android.ui.latest

import co.touchlab.kermit.Logger
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.dsl.module
import ru.akella.cryptocoin.android.ui.latest.mvi.LatestReducer
import ru.akella.cryptocoin.android.ui.latest.mvi.LatestStoreFactory
import ru.akella.cryptocoin.android.ui.latest.mvi.LatestStoreFactoryImpl
import ru.akella.cryptocoin.getWith

val latestModule = module {
    factory { LatestScreenModel(get(), get(), getWith<Logger>("LatestScreenModel"), get(), get()) }
    factory<LatestStoreFactory> { LatestStoreFactoryImpl(DefaultStoreFactory(), get()) }
    factory { LatestReducer() }
}