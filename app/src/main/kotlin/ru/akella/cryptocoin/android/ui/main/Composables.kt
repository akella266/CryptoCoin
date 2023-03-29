package ru.akella.cryptocoin.android.ui.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import co.touchlab.kermit.Logger
import ru.akella.cryptocoin.android.ui.about.AboutScreen
import ru.akella.cryptocoin.android.ui.categories.CategoriesScreen
import ru.akella.cryptocoin.android.ui.exchanges.ExchangesScreen
import ru.akella.cryptocoin.android.ui.latest.LatestScreen
import ru.akella.cryptocoin.models.MainViewModel

data class MainScreen(
    private val viewModel: MainViewModel,
    private val log: Logger,
) : Screen {

    @Composable
    override fun Content() {
        MainScreenContent()
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreenContent() {
    TabNavigator(LatestScreen) {
        Scaffold(
            content = { CurrentTab() },
            bottomBar = {
                BottomNavigation {
                    TabNavigationItem(LatestScreen)
                    TabNavigationItem(CategoriesScreen)
                    TabNavigationItem(ExchangesScreen)
                    TabNavigationItem(AboutScreen)
                }
            }
        )
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    BottomNavigationItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = {
            with(tab.options) {
                icon?.let {
                    Icon(painter = it, contentDescription = title)
                }
            }
        }
    )
}

@Preview
@Composable
fun MainScreenContentPreview_Success() {
    MainScreenContent()
}
