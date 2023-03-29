package ru.akella.cryptocoin.android.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cafe.adriel.voyager.navigator.Navigator
import ru.akella.cryptocoin.android.ui.theme.AppTheme
import ru.akella.cryptocoin.injectLogger
import ru.akella.cryptocoin.models.MainViewModel
import co.touchlab.kermit.Logger
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class MainActivity : ComponentActivity(), KoinComponent {

    private val log: Logger by injectLogger("MainActivity")
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Navigator(MainScreen(viewModel, log))
            }
        }
    }
}
