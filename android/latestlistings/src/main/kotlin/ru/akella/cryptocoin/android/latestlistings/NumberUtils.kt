package ru.akella.cryptocoin.android.latestlistings

fun formatCap(cap: Double): String {
    return when {
        cap / 1_000_000_000 > 0 -> String.format("%3.2f Bn", cap)
        cap / 1_000_000 > 0 -> String.format("%3.2f M", cap)
        cap / 1_000 > 0 -> String.format("%3.2f T", cap)
        else -> String.format("%3.2f", cap)
    }
}
