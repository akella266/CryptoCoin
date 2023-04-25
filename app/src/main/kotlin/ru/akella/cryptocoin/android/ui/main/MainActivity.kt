package ru.akella.cryptocoin.android.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cafe.adriel.voyager.navigator.Navigator
import co.touchlab.kermit.Logger
import org.koin.core.component.KoinComponent
import ru.akella.cryptocoin.android.ui.theme.AppTheme
import ru.akella.cryptocoin.injectLogger

class MainActivity : ComponentActivity(), KoinComponent {

    private val log: Logger by injectLogger("MainActivity")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Navigator(MainScreen(log))
            }
        }
    }
}
