package ru.akella.cryptocoin.domain

import org.koin.dsl.module
import ru.akella.cryptocoin.domain.interactors.GetLatestListsInteractor

val domainModule = module {
    factory { GetLatestListsInteractor(get(), get()) }
}