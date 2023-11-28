package ru.akella.cryptocoin.data.db.mappers

import ru.akella.cryptocoin.data.db.models.CoinDbModel

internal fun toDbModel(
    id: String,
    categoryId: String,
    img: String?,
    name: String,
    currentCost: Double,
    lastChangePercent: Double,
    cap: Double,
    description: String
) = CoinDbModel(
        id = id,
        categoryId = categoryId,
        img = img,
        name = name,
        currentCost = currentCost,
        lastChangePercent = lastChangePercent,
        cap = cap,
        description = description,
    )