package ru.akella.cryptocoin.android.ui

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import cafe.adriel.voyager.core.registry.screenModule
import org.koin.dsl.module
import ru.akella.cryptocoin.AppInfo
import ru.akella.cryptocoin.android.BuildConfig
import ru.akella.cryptocoin.android.ui.latest.latestModule
import ru.akella.cryptocoin.initKoin

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(
            module {
                single<Context> { this@MainApp }
                single<SharedPreferences> {
                    get<Context>().getSharedPreferences("CRYPTOCOIN_SETTINGS", MODE_PRIVATE)
                }
                single<AppInfo> { AndroidAppInfo }
                single {
                    { Log.i("Startup", "Hello from Android/Kotlin!") }
                }
            }.apply {
                includes(latestModule)
            }
        )
    }
}

object AndroidAppInfo : AppInfo {
    override val appId: String = BuildConfig.APPLICATION_ID
}
