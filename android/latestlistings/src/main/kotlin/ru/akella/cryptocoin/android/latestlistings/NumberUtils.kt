package ru.akella.cryptocoin.android.latestlistings

import java.util.Locale

fun formatCap(cap: Double): String {
    return when {
        cap / 1_000_000_000 > 0 -> String.format(Locale.getDefault(), "%3.2f Bn", cap / 1_000_000_000)
        cap / 1_000_000 > 0 -> String.format(Locale.getDefault(),"%3.2f M", cap / 1_000_000)
        cap / 1_000 > 0 -> String.format(Locale.getDefault(),"%3.2f T", cap / 1_000)
        else -> String.format(Locale.getDefault(),"%3.2f", cap)
    }
}
