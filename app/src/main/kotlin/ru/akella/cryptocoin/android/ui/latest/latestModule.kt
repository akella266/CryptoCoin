package ru.akella.cryptocoin.android.ui.latest

import co.touchlab.kermit.Logger
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.dsl.module
import ru.akella.cryptocoin.DispatchersProvider
import ru.akella.cryptocoin.android.ui.ResourceProvider
import ru.akella.cryptocoin.android.ui.latest.mvi.LatestReducer
import ru.akella.cryptocoin.android.ui.latest.mvi.LatestStoreFactory
import ru.akella.cryptocoin.android.ui.latest.mvi.LatestStoreFactoryImpl
import ru.akella.cryptocoin.domain.interactors.GetLatestListsInteractor
import ru.akella.cryptocoin.getWith

val latestModule = module {
    factory {
        LatestScreenModel(
            getWith<Logger>("LatestScreenModel"),
            get<LatestStoreFactory>(),
            get<DispatchersProvider>(),
            get<GetLatestListsInteractor>(),
            get<ResourceProvider>(),
        )
    }
    factory<LatestStoreFactory> { LatestStoreFactoryImpl(DefaultStoreFactory(), get()) }
    factory { LatestReducer() }
}