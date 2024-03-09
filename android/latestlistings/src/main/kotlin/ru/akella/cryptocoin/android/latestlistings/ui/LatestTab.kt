package ru.akella.cryptocoin.android.latestlistings.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import co.touchlab.kermit.Logger
import ru.akella.cryptocoin.android.latestlistings.model.LatestState
import ru.akella.cryptocoin.android.core.R as CoreR

class LatestTab(private val log: Logger) : Tab {

    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 1u,
            title = stringResource(id = CoreR.string.main_bottom_tab_latest),
            icon = painterResource(id = CoreR.drawable.ic_latest)
        )

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<LatestScreenModel>()
        val state by screenModel.states.collectAsState(initial = LatestState())

        LatestContent(
            state,
            screenModel::refresh,
            screenModel::search,
            screenModel::applyMarketCapSort,
            screenModel::applyPriceSort,
            screenModel::applyDailyVolumeSort
        )

        LaunchedEffect(key1 = screenModel) {
            screenModel.loadListings()
        }
    }
}
