package ru.akella.cryptocoin.android.core

import android.content.Context
import org.koin.dsl.module

val baseModule = module {
    factory { ResourceProvider(get<Context>()) }
}