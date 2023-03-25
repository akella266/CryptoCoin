package ru.akella.cryptocoin.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.akella.cryptocoin.android.ui.theme.KaMPKitTheme
import ru.akella.cryptocoin.injectLogger
import ru.akella.cryptocoin.models.BreedViewModel
import co.touchlab.kermit.Logger
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class MainActivity : ComponentActivity(), KoinComponent {

    private val log: Logger by injectLogger("MainActivity")
    private val viewModel: BreedViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KaMPKitTheme {
                MainScreen(viewModel, log)
            }
        }
    }
}
