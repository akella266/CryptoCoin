package ru.akella.cryptocoin.android.ui.common

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import ru.akella.cryptocoin.android.R

@Composable
fun EmptyText(modifier: Modifier = Modifier, @StringRes textRes: Int = R.string.empty_data) {
    Box(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = textRes),
            fontSize = 14.sp,
        )
    }
}