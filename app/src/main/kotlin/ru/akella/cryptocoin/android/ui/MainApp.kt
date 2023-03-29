package ru.akella.cryptocoin.android.ui

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import ru.akella.cryptocoin.AppInfo
import ru.akella.cryptocoin.initKoin
import ru.akella.cryptocoin.models.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import ru.akella.cryptocoin.android.BuildConfig

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(
            module {
                single<Context> { this@MainApp }
                viewModel { MainViewModel(get(), get { parametersOf("BreedViewModel") }) }
                single<SharedPreferences> {
                    get<Context>().getSharedPreferences("KAMPSTARTER_SETTINGS", MODE_PRIVATE)
                }
                single<AppInfo> { AndroidAppInfo }
                single {
                    { Log.i("Startup", "Hello from Android/Kotlin!") }
                }
            }
        )
    }
}

object AndroidAppInfo : AppInfo {
    override val appId: String = BuildConfig.APPLICATION_ID
}
