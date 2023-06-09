package ru.akella.cryptocoin

import co.touchlab.kermit.Logger
import org.koin.core.context.stopKoin
import org.koin.core.parameter.parametersOf
import org.koin.test.check.checkModules
import platform.Foundation.NSUserDefaults
import ru.akella.cryptocoin.TestAppInfo
import ru.akella.cryptocoin.initKoinIos
import kotlin.test.AfterTest
import kotlin.test.Test

class KoinTest {
    @Test
    fun checkAllModules() {
        initKoinIos(
            userDefaults = NSUserDefaults.standardUserDefaults,
            appInfo = TestAppInfo,
            doOnStartup = { }
        ).checkModules {
            withParameters<Logger> { parametersOf("TestTag") }
        }
    }

    @AfterTest
    fun breakdown() {
        stopKoin()
    }
}
