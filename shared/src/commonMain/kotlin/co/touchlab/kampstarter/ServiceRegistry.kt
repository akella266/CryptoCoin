package co.touchlab.kampstarter

import co.touchlab.kampstarter.ktor.DogApiImpl
import co.touchlab.kampstarter.ktor.KtorApi
import com.russhwolf.settings.Settings
import org.koin.core.context.startKoin
import org.koin.dsl.module

object ServiceRegistry {
    fun appStart(helper: DatabaseHelper, settings: Settings) {
        val coreModule = module {
            single { helper }
            single { settings }
            single { DogApiImpl as KtorApi }
        }

        startKoin { modules(coreModule) }
    }
}