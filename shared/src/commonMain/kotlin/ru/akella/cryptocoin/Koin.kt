package ru.akella.cryptocoin

import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import co.touchlab.kermit.platformLogWriter
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.ProxyBuilder
import io.ktor.client.engine.http
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpSendPipeline
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
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
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.StringQualifier
import org.koin.core.scope.Scope
import org.koin.dsl.module
import ru.akella.cryptocoin.data.api.BASE_URL
import ru.akella.cryptocoin.data.api.CoinMarketCapApi
import ru.akella.cryptocoin.data.repositories.CryptoCurrencyRepository
import ru.akella.cryptocoin.data.repositories.ICryptoCurrencyRepository
import ru.akella.cryptocoin.domain.AuthHeaders
import ru.akella.cryptocoin.domain.BadRequestException
import ru.akella.cryptocoin.domain.ForbiddenException
import ru.akella.cryptocoin.domain.NotFoundException
import ru.akella.cryptocoin.domain.ServerException
import ru.akella.cryptocoin.domain.TooManyRequestsException
import ru.akella.cryptocoin.domain.UnauthorizedException
import ru.akella.cryptocoin.domain.createHttpClient
import ru.akella.cryptocoin.domain.createJson
import ru.akella.cryptocoin.domain.domainModule

fun initKoin(appModule: Module): KoinApplication {
    val koinApplication = startKoin {
        modules(
            appModule,
            platformModule,
            coreModule,
            domainModule
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

    factory<ICryptoCurrencyRepository> {
        CryptoCurrencyRepository(
            get(),
            get(),
            get(),
            getWith<Logger>("CryptoCurrencyRepository"),
            get()
        )
    }
}

inline fun <reified T> Scope.getWith(vararg params: Any?): T {
    return get(parameters = { parametersOf(*params) })
}

// Simple function to clean up the syntax a bit
fun KoinComponent.injectLogger(tag: String): Lazy<Logger> = inject { parametersOf(tag) }

expect val platformModule: Module
