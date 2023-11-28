package ru.akella.cryptocoin.data.db.mappers

import ru.akella.cryptocoin.data.db.models.CategoryDbModel

internal fun toDbModel(
    id: String,
    description: String,
    tradingVolume: Double,
    cap: Double
) = CategoryDbModel(
        id,
        description,
        tradingVolume,
        cap
    )
