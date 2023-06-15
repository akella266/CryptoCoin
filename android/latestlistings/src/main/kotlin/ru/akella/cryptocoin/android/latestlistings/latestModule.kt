package ru.akella.cryptocoin.android.latestlistings

import co.touchlab.kermit.Logger
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.dsl.module
import ru.akella.cryptocoin.DispatchersProvider
import ru.akella.cryptocoin.android.core.ResourceProvider
import ru.akella.cryptocoin.android.latestlistings.model.LatestExecutor
import ru.akella.cryptocoin.android.latestlistings.model.LatestReducer
import ru.akella.cryptocoin.android.latestlistings.model.LatestStoreFactory
import ru.akella.cryptocoin.android.latestlistings.model.LatestStoreFactoryImpl
import ru.akella.cryptocoin.android.latestlistings.ui.LatestScreenModel
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
    factory<LatestStoreFactory> {
        LatestStoreFactoryImpl(
            DefaultStoreFactory(),
            get<LatestReducer>(),
            get<LatestExecutor>()
        )
    }
    factory { LatestReducer() }
    factory { LatestExecutor(get<GetLatestListsInteractor>(), get<DispatchersProvider>()) }
}