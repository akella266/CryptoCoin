package ru.akella.cryptocoin.android.ui

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import coil.ImageLoader
import okhttp3.OkHttpClient
import org.koin.dsl.module
import ru.akella.cryptocoin.AppInfo
import ru.akella.cryptocoin.android.BuildConfig
import ru.akella.cryptocoin.android.ui.latest.latestModule
import ru.akella.cryptocoin.domain.AuthHeaders
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

                single<ImageLoader> {
                    val headers = get<AuthHeaders>()
                    ImageLoader.Builder(get<Context>())
                        .okHttpClient {
                            OkHttpClient.Builder()
                                .addNetworkInterceptor { chain ->
                                    val request = chain.request().newBuilder()
                                        .header(headers.name, headers.value)
                                        .build()
                                    chain.proceed(request)
                                }
                                .build()
                        }
                        .build()
                }

                factory { ResourceProvider(get<Context>()) }
            }.apply {
                includes(latestModule)
            }
        )
    }
}

object AndroidAppInfo : AppInfo {
    override val appId: String = BuildConfig.APPLICATION_ID
}
