package ru.akella.cryptocoin

import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import co.touchlab.kermit.platformLogWriter
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpSendInterceptor
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpSendPipeline
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope
import org.koin.dsl.module
import ru.akella.cryptocoin.data.api.CoinMarketCapApi
import ru.akella.cryptocoin.data.repositories.CryptoCurrencyRepository
import ru.akella.cryptocoin.data.repositories.ICryptoCurrencyRepository

fun initKoin(appModule: Module): KoinApplication {
    val koinApplication = startKoin {
        modules(
            appModule,
            platformModule,
            coreModule
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
    single { createJson() }
    single { createHttpClient(get(), get(), get()) }

    single {
        DatabaseHelper(
            get(),
            getWith("DatabaseHelper"),
            Dispatchers.Default
        )
    }
    single { CoinMarketCapApi(getWith("CoinMarketCapApi"), get()) }
    single<Clock> { Clock.System }

    // platformLogWriter() is a relatively simple config option, useful for local debugging. For production
    // uses you *may* want to have a more robust configuration from the native platform. In KaMP Kit,
    // that would likely go into platformModule expect/actual.
    // See https://github.com/touchlab/Kermit
    val baseLogger = Logger(config = StaticConfig(logWriterList = listOf(platformLogWriter())), "KampKit")
    factory { (tag: String?) -> if (tag != null) baseLogger.withTag(tag) else baseLogger }

    factory<ICryptoCurrencyRepository> {
        CryptoCurrencyRepository(get(), get(), get(), get(), get())
    }
}

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
}

fun createHttpClient(
    httpClientEngine: HttpClientEngine,
    json: Json,
    log: Logger
) = HttpClient(httpClientEngine) {

    install(ContentNegotiation) {
        json(json)
    }

    install(Logging) {
        logger = object : io.ktor.client.plugins.logging.Logger {
            override fun log(message: String) {
                log.v { message }
            }
        }

        level = LogLevel.INFO
    }
    install(HttpTimeout) {
        val timeout = 30000L
        connectTimeoutMillis = timeout
        requestTimeoutMillis = timeout
        socketTimeoutMillis = timeout
    }
}.apply {
    sendPipeline.intercept(HttpSendPipeline.State) {
        context.headers.append("Accept", "application/json")
        context.headers.append("Accept-Encoding", "deflate, gzip")
        context.headers.append("X-CMC_PRO_API_KEY", "d0f5d425-be11-49c9-8dac-83f57df88cc2")
    }
}

internal inline fun <reified T> Scope.getWith(vararg params: Any?): T {
    return get(parameters = { parametersOf(*params) })
}

// Simple function to clean up the syntax a bit
fun KoinComponent.injectLogger(tag: String): Lazy<Logger> = inject { parametersOf(tag) }

expect val platformModule: Module
