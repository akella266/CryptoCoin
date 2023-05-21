package ru.akella.cryptocoin.android.ui.latest

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.node.modifierElementOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import ru.akella.cryptocoin.android.R
import ru.akella.cryptocoin.android.ui.theme.AppTheme
import ru.akella.cryptocoin.android.ui.theme.Green
import ru.akella.cryptocoin.android.ui.theme.Green70

@OptIn(ExperimentalMaterialApi::class)
object LatestScreen : Tab {

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
        ScreenContent()
    }

    @Composable
    fun ScreenContent() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
                .padding(top = 8.dp),
        ) {
            SearchField()
            LazyColumn() {
                items()
            }
        }
    }

    @Composable
    private fun SearchField(modifier: Modifier = Modifier) {
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            value = "Search for tokens...",
            singleLine = true,
            onValueChange = { },
            leadingIcon = {
                Image(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.ic_search_24),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.Gray)
                )
            },
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.LightGray,
            )
        )
    }

    @Composable
    fun CoinsHeader(modifier: Modifier = Modifier) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(0.05f, true),
                text = "1",
                fontSize = 12.sp,
                color = Color.Black
            )
            Image(
                modifier = Modifier.weight(0.1f, true),
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
            )
            Text(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .weight(0.4f, true),
                text = "Bitcoin",
                fontSize = 12.sp,
                color = Color.Black,
            )
            Text(
                modifier = Modifier.weight(0.3f, true),
                text = "21000.56",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            PriceChange(
                modifier = Modifier
                    .padding(end = 16.dp)
            )
        }
    }

    @Composable
    fun HeaderItem(modifier: Modifier = Modifier) {
        val rotation = -90f
        val colorFilter = null
        Row(
            modifier = modifier,
        ) {
            Text(
                modifier = Modifier,
                text = "Market cap",
                fontSize = 10.sp
            )
            Image(
                modifier = Modifier
                    .rotate(rotation),
                painter = painterResource(id = R.drawable.ic_arrow_16),
                contentDescription = null,
                colorFilter = colorFilter,
            )
        }
    }

    @Composable
    fun CoinItem(modifier: Modifier = Modifier) {
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
                text = "1",
                fontSize = 12.sp,
                color = Color.Black
            )
            Image(
                modifier = Modifier.weight(0.1f, true),
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
            )
            Text(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .weight(0.4f, true),
                text = "Bitcoin",
                fontSize = 12.sp,
                color = Color.Black,
            )
            Text(
                modifier = Modifier.weight(0.3f, true),
                text = "21000.56",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            PriceChange(
                modifier = Modifier
                    .padding(end = 16.dp)
            )
        }
    }

    @Composable
    private fun PriceChange(
        modifier: Modifier = Modifier
    ) {
        val rotation = -90f
        val color = Color.Green
        val backgroundColor = Green70
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
            Image(
                modifier = Modifier
                    .size(16.dp)
                    .rotate(rotation),
                painter = painterResource(id = R.drawable.ic_arrow_16),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = color),
            )
            Text(
                text = "10.23 %",
                color = color,
                fontSize = 12.sp,
                maxLines = 1,
            )
        }
    }
}

@Preview
@Composable
fun LatestScreenPreview() {
    AppTheme {
        LatestScreen.ScreenContent()
    }
}

@Preview(backgroundColor = 0xfff)
@Composable
fun CoinItemPreview() {
    AppTheme {
        LatestScreen.CoinItem()
    }
}

@Preview
@Composable
fun HeaderPreview() {
    AppTheme {
        LatestScreen.HeaderItem(Modifier)
    }
}