package ru.akella.cryptocoin.android.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import cafe.adriel.voyager.navigator.Navigator
import co.touchlab.kermit.Logger
import coil.ImageLoader
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent
import ru.akella.cryptocoin.android.core.custom.LocalImageLoader
import ru.akella.cryptocoin.android.core.theme.AppTheme
import ru.akella.cryptocoin.injectLogger

class MainActivity : ComponentActivity(), KoinComponent {

    private val log: Logger by injectLogger("MainActivity")

    private val imageLoader by inject<ImageLoader>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(LocalImageLoader provides imageLoader) {
                AppTheme {
                    Navigator(MainScreen(log))
                }
            }
        }
    }
}
