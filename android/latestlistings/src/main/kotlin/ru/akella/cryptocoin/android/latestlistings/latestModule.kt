package ru.akella.cryptocoin.android.latestlistings

import co.touchlab.kermit.Logger
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.dsl.module
import ru.akella.cryptocoin.DispatchersProvider
import ru.akella.cryptocoin.android.latestlistings.mvi.LatestReducer
import ru.akella.cryptocoin.android.latestlistings.mvi.LatestStoreFactory
import ru.akella.cryptocoin.android.latestlistings.mvi.LatestStoreFactoryImpl
import ru.akella.cryptocoin.domain.interactors.GetLatestListsInteractor
import ru.akella.cryptocoin.getWith

val latestModule = module {
    factory {
        LatestScreenModel(
            getWith<Logger>("LatestScreenModel"),
            get<LatestStoreFactory>(),
            get<DispatchersProvider>(),
            get<GetLatestListsInteractor>(),
            get<ru.akella.cryptocoin.android.core.ResourceProvider>(),
        )
    }
    factory<LatestStoreFactory> { LatestStoreFactoryImpl(DefaultStoreFactory(), get()) }
    factory { LatestReducer() }
}