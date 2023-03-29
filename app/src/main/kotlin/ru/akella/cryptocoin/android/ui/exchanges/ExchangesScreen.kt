package ru.akella.cryptocoin.android.ui.exchanges

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import ru.akella.cryptocoin.android.R
import ru.akella.cryptocoin.android.ui.latest.LatestScreen

object ExchangesScreen : Tab {

    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 2u,
            title = stringResource(id = R.string.main_bottom_tab_exchanges),
            icon = painterResource(id = R.drawable.ic_exchanges)
        )

    @Composable
    override fun Content() {
        Box(contentAlignment = Alignment.Center) {
            Text(text = options.title)
        }
    }
}