package ru.akella.cryptocoin

import com.squareup.sqldelight.db.SqlDriver

internal expect fun testDbConnection(): SqlDriver
