package ru.akella.cryptocoin.android.latestlistings.ui

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import ru.akella.cryptocoin.android.core.EMPTY
import ru.akella.cryptocoin.android.core.R as CoreR
import ru.akella.cryptocoin.android.core.custom.AsyncImage
import ru.akella.cryptocoin.android.core.custom.EmptyText
import ru.akella.cryptocoin.android.core.theme.AppTheme
import ru.akella.cryptocoin.android.latestlistings.formatCap
import ru.akella.cryptocoin.android.latestlistings.model.LatestState
import ru.akella.cryptocoin.android.latestlistings.model.SortField
import ru.akella.cryptocoin.data.api.SortDirection
import ru.akella.cryptocoin.domain.models.Coin
import kotlin.math.abs
import kotlin.math.round
import ru.akella.cryptocoin.android.latestlistings.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LatestContent(
    state: LatestState,
    onRefresh: () -> Unit,
    onQueryChanged: (String) -> Unit,
    applyMarketCapSort: () -> Unit,
    applyPriceSort: () -> Unit,
    applyDailyVolumeSort: () -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isLoading,
        onRefresh = onRefresh,
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
                .padding(horizontal = 12.dp, vertical = 16.dp)
                .background(
                    color = MaterialTheme.colors.onBackground,
                    shape = RoundedCornerShape(16.dp)
                )
                .pullRefresh(pullRefreshState)
        ) {
            val coins = state.data

            if (coins.isNullOrEmpty()) {
                EmptyText()
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                ) {
                    item {
                        Header(
                            Modifier,
                            state.sortDirection,
                            state.activeSortField,
                            applyMarketCapSort,
                            applyPriceSort,
                            applyDailyVolumeSort,
                        )
                    }
                    items(coins) {
                        CoinItem(
                            number = coins.indexOf(it) + 1,
                            coin = it
                        )
                    }
                }
            }

            // todo need to migrate to material3 to avoid keyFrame crash
            // PullRefreshIndicator(
            //     state.isLoading,
            //     pullRefreshState,
            //     Modifier.align(Alignment.TopCenter)
            // )
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
            .height(36.dp)
            .padding(horizontal = 12.dp),
        label = { Text("Search for tokens...") },
        value = String.EMPTY,
        singleLine = true,
        onValueChange = onQueryChanged,
        leadingIcon = {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = CoreR.drawable.ic_search_24),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Gray)
            )
        },
        shape = RoundedCornerShape(24.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
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
            fontSize = 14.sp,
            color = Color.White
        )
        AsyncImage(
            modifier = Modifier
                .height(32.dp)
                .weight(0.1f, true),
            model = coin.img,
            contentDescription = null,
            error = CoreR.drawable.ic_placeholder_24
        )
        Column(
            modifier = Modifier
                .padding(start = 4.dp)
                .weight(0.4f, true)
        ) {
            Text(
                text = coin.name,
                fontSize = 16.sp,
                color = Color.White,
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = formatCap(coin.cap),
                fontSize = 14.sp,
                color = Color.White,
            )
        }
        Text(
            modifier = Modifier
                .weight(0.3f, true),
            text = String.format("%.2f", coin.currentCost),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.End
        )
        PriceChange(
            modifier = Modifier
                .padding(start = 16.dp, end = 12.dp),
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
            changePercent < 0f -> ru.akella.cryptocoin.android.core.theme.Red70
            changePercent > 0f -> ru.akella.cryptocoin.android.core.theme.Green70
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
                painter = painterResource(id = CoreR.drawable.ic_arrow_16),
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
    sortDirection: SortDirection?,
    sortField: SortField?,
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
                .padding(start = 32.dp, end = 12.dp)
                .weight(0.4f, true)
                .clickable(onClick = onCapSortClicked),
            isActive = sortField == SortField.MARKET_CAP,
            sortDirection = sortDirection,
            title = stringResource(id = R.string.latest_header_market_cap),
            arrangement = Arrangement.Start,
        )
        HeaderItem(
            modifier = Modifier
                .padding(start = 2.dp)
                .weight(0.3f, true)
                .clickable(onClick = onPriceSortClicked),
            isActive = sortField == SortField.PRICE,
            sortDirection = sortDirection,
            title = stringResource(id = R.string.latest_header_cost),
            arrangement = Arrangement.End,
        )
        HeaderItem(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp)
                .weight(0.2f, true)
                .clickable(onClick = onPerDayChangeSortClicked),
            isActive = sortField == SortField.DAILY,
            sortDirection = sortDirection,
            title = stringResource(id = R.string.latest_header_24h),
            arrangement = Arrangement.End,
        )
    }
}

@Composable
fun HeaderItem(
    modifier: Modifier = Modifier,
    title: String = "",
    arrangement: Arrangement.Horizontal = Arrangement.Start,
    isActive: Boolean = false,
    sortDirection: SortDirection?,
) {
    val rotation =
        if (sortDirection == null || sortDirection == SortDirection.DESC) -90f else 90f
    val color = if (isActive) Color.Blue else Color.Gray
    val colorFilter = ColorFilter.tint(color)
    Row(
        modifier = modifier,
        horizontalArrangement = arrangement,
    ) {
        Text(
            modifier = Modifier,
            text = title,
            color = Color.White,
            fontSize = 14.sp,
        )
        if (sortDirection != null) {
            Image(
                modifier = Modifier
                    .rotate(rotation),
                painter = painterResource(id = CoreR.drawable.ic_arrow_16),
                contentDescription = null,
                colorFilter = colorFilter,
            )
        }
    }
}

@Preview
@Composable
fun LatestScreenPreview() {
    AppTheme {
        LatestContent(
            LatestState(
                data = listOf(
                    Coin("id", "", null, "name", 12.34, 34.56, 56.67, "description"),
                    Coin("id", "", null, "name", 12.34, 34.56, 56.67, "description"),
                    Coin("id", "", null, "name", 12.34, 34.56, 56.67, "description"),
                    Coin("id", "", null, "name", 12.34, 34.56, 56.67, "description"),
                    Coin("id", "", null, "name", 12.34, 34.56, 56.67, "description"),
                )
            ),
            onRefresh = {},
            onQueryChanged = {},
            applyPriceSort =  {},
            applyDailyVolumeSort = {},
            applyMarketCapSort = {}
        )
    }
}
