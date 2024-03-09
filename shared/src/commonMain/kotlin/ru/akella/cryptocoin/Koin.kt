package ru.akella.cryptocoin

import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import co.touchlab.kermit.platformLogWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.StringQualifier
import org.koin.core.scope.Scope
import org.koin.dsl.module
import ru.akella.cryptocoin.data.api.BASE_URL
import ru.akella.cryptocoin.data.api.CoinMarketCapApi
import ru.akella.cryptocoin.data.db.dbModule
import ru.akella.cryptocoin.domain.AuthHeaders
import ru.akella.cryptocoin.domain.createHttpClient
import ru.akella.cryptocoin.domain.createJson
import ru.akella.cryptocoin.domain.domainModule

fun initKoin(appModule: Module): KoinApplication {
    val koinApplication = startKoin {
        modules(
            appModule,
            platformModule,
            coreModule,
            domainModule,
            dbModule,
        )
    }

    // Dummy initialization logic, making use of appModule declarations for demonstration purposes.
    val koin = koinApplication.koin
    // doOnStartup is a lambda which is implemented in Swift on iOS side
    val doOnStartup = koin.get<() -> Unit>()
    doOnStartup.invoke()

    val kermit = koin.get<Logger> { parametersOf(null) }
    // AppInfo is a Kotlin interface with separate Android and iOS implementations
    val appInfo = koin.get<AppInfo>()
    kermit.v { "App Id ${appInfo.appId}" }

    return koinApplication
}

private val coreModule = module {
    single<String>(qualifier = StringQualifier("BaseUrl")) { BASE_URL }
    single { AuthHeaders() }
    single { createJson() }
    single { createHttpClient(get(), get(), get(), getWith<Logger>("HttpClient")) }

    single {
        DatabaseHelper(
            get(),
            getWith("DatabaseHelper"),
            Dispatchers.Default
        )
    }
    single { CoinMarketCapApi(get(), get(qualifier = StringQualifier("BaseUrl"))) }
    single<Clock> { Clock.System }
    
    val baseLogger =
        Logger(config = StaticConfig(logWriterList = listOf(platformLogWriter())), "CryptoCoin")
    factory { (tag: String?) -> if (tag != null) baseLogger.withTag(tag) else baseLogger }
}



inline fun <reified T> Scope.getWith(vararg params: Any?): T {
    return get(parameters = { parametersOf(*params) })
}

// Simple function to clean up the syntax a bit
fun KoinComponent.injectLogger(tag: String): Lazy<Logger> = inject { parametersOf(tag) }

expect val platformModule: Module
