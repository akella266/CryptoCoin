package ru.akella.cryptocoin.android.ui.latest

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.akella.cryptocoin.android.ui.latest.mvi.LatestReducer
import ru.akella.cryptocoin.android.ui.latest.mvi.LatestStoreFactory
import ru.akella.cryptocoin.android.ui.latest.mvi.LatestStoreFactoryImpl

val latestModule = module {
    factory { LatestScreenModel(get()) }
    factory<LatestStoreFactory> { LatestStoreFactoryImpl(DefaultStoreFactory(), get()) }
    factory { LatestReducer() }
}