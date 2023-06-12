package ru.akella.cryptocoin.android.ui.latest

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import co.touchlab.kermit.Logger
import ru.akella.cryptocoin.android.R
import ru.akella.cryptocoin.android.ui.base.AsyncImage
import ru.akella.cryptocoin.android.ui.common.EmptyText
import ru.akella.cryptocoin.android.ui.latest.mvi.LatestState
import ru.akella.cryptocoin.android.ui.theme.AppTheme
import ru.akella.cryptocoin.android.ui.theme.Green70
import ru.akella.cryptocoin.android.ui.theme.Red70
import ru.akella.cryptocoin.android.util.EMPTY
import ru.akella.cryptocoin.android.util.formatCap
import ru.akella.cryptocoin.domain.models.Coin
import kotlin.math.abs
import kotlin.math.round

@OptIn(ExperimentalMaterialApi::class)
class LatestScreen(private val log: Logger) : Tab {

    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 1u,
            title = stringResource(id = R.string.main_bottom_tab_latest),
            icon = painterResource(id = R.drawable.ic_latest)
        )

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<LatestScreenModel>()
        val state = screenModel.states.collectAsState(initial = LatestState())
        ScreenContent(
            state.value.data,
            state.value.isLoading,
            state.value.errorMessage,
            refresh = screenModel::refresh,
            onQueryChanged = screenModel::search
        )

        LaunchedEffect(key1 = screenModel) {
            screenModel.loadListings()
        }
    }

    @Composable
    fun ScreenContent(
        coins: List<Coin>?,
        isLoading: Boolean,
        errorMessage: String?,
        refresh: () -> Unit,
        onQueryChanged: (String) -> Unit,
    ) {
        val pullRefreshState = rememberPullRefreshState(
            refreshing = isLoading,
            onRefresh = refresh,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
                .padding(top = 8.dp),
        ) {
            SearchField(onQueryChanged = onQueryChanged)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
            ) {
                if (coins.isNullOrEmpty()) {
                    EmptyText()
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 12.dp)
                    ) {
                        item { Header(Modifier, {}, {}, {}) }
                        items(coins) {
                            CoinItem(
                                number = coins.indexOf(it) + 1,
                                coin = it
                            )
                        }
                    }
                }

                PullRefreshIndicator(
                    isLoading,
                    pullRefreshState,
                    Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }

    @Composable
    private fun SearchField(
        modifier: Modifier = Modifier,
        onQueryChanged: (String) -> Unit,
        ) {
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            label = { Text("Search for tokens...") },
            value = String.EMPTY,
            singleLine = true,
            onValueChange = onQueryChanged,
            leadingIcon = {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_search_24),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.Gray)
                )
            },
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.LightGray,
            )
        )
    }

    @Composable
    fun CoinItem(
        modifier: Modifier = Modifier,
        number: Int,
        coin: Coin,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(0.1f, true),
                text = number.toString(),
                fontSize = 12.sp,
                color = Color.Black
            )
            AsyncImage(
                modifier = Modifier
                    .height(32.dp)
                    .weight(0.1f, true),
                model = coin.img,
                contentDescription = null,
            )
            Column(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .weight(0.4f, true)
            ) {
                Text(
                    text = coin.name,
                    fontSize = 12.sp,
                    color = Color.Black,
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = formatCap(coin.cap),
                    fontSize = 12.sp,
                    color = Color.Black,
                )
            }
            Text(
                modifier = Modifier.weight(0.3f, true),
                text = String.format("%.2f", coin.currentCost),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            PriceChange(
                modifier = Modifier
                    .padding(end = 16.dp),
                changePercent = coin.lastChangePercent
            )
        }
    }

    @Composable
    private fun PriceChange(
        modifier: Modifier = Modifier,
        changePercent: Double,
    ) {
        val rotation = when {
            changePercent < 0f -> -90f
            else -> 90f
        }
        val color = when {
            changePercent < 0f -> Color.Red
            changePercent > 0f -> Color.Green
            else -> Color.Gray
        }

        val backgroundColor =
            when {
                changePercent < 0f -> Red70
                changePercent > 0f -> Green70
                else -> Color.LightGray
            }
        Row(
            modifier = modifier
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 4.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            if (round(changePercent * 100) / 100 != 0.00) {
                Image(
                    modifier = Modifier
                        .size(16.dp)
                        .rotate(rotation),
                    painter = painterResource(id = R.drawable.ic_arrow_16),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = color),
                )
            } else {
                Spacer(modifier = Modifier.size(16.dp))
            }
            Text(
                text = String.format("%.2f", abs(changePercent)),
                color = color,
                fontSize = 12.sp,
                maxLines = 1,
            )
        }
    }

    @Composable
    fun Header(
        modifier: Modifier = Modifier,
        onCapSortClicked: () -> Unit,
        onPriceSortClicked: () -> Unit,
        onPerDayChangeSortClicked: () -> Unit,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HeaderItem(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .weight(0.6f, true)
                    .clickable(onClick = onCapSortClicked),
                stringResource(id = R.string.latest_header_market_cap),
            )
            HeaderItem(
                modifier = Modifier
                    .padding(start = 2.dp)
                    .weight(0.4f, true)
                    .clickable(onClick = onPriceSortClicked),
                stringResource(id = R.string.latest_header_cost)
            )
            HeaderItem(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(0.3f, true)
                    .clickable(onClick = onPerDayChangeSortClicked),
                stringResource(id = R.string.latest_header_24h)
            )
        }
    }

    @Composable
    fun HeaderItem(
        modifier: Modifier = Modifier,
        title: String = "",
        hideSort: Boolean = false,
    ) {
        val rotation = -90f
        val colorFilter = ColorFilter.tint(Color.Gray)
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier,
                text = title,
                color = Color.Black,
                fontSize = 10.sp
            )
            if (!hideSort) {
                Image(
                    modifier = Modifier
                        .rotate(rotation),
                    painter = painterResource(id = R.drawable.ic_arrow_16),
                    contentDescription = null,
                    colorFilter = colorFilter,
                )
            }
        }
    }
}

@Preview
@Composable
fun LatestScreenPreview() {
    AppTheme {
        LatestScreen(Logger).ScreenContent(
            coins = coins,
            true,
            "",
            {}
        ) { }
    }
}
