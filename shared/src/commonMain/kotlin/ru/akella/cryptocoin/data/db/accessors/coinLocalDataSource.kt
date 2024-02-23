package ru.akella.cryptocoin.data.db.accessors

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import ru.akella.cryptocoin.DispatchersProvider
import ru.akella.cryptocoin.data.db.mappers.toDbModel
import ru.akella.cryptocoin.data.db.models.CoinDbModel
import ru.akella.cryptocoin.data.db.transactionWithContext
import ru.akella.cryptocoin.db.CryptoCoin

interface ICoinLocalDataSource {

    suspend fun put(categoryId: String, coin: CoinDbModel)

    fun get(id: String): Flow<CoinDbModel>

    fun getAll(): Flow<List<CoinDbModel>>

    suspend fun deleteAll()
}

internal class CoinLocalDataSource(
    sqlDriver: SqlDriver,
    private val backgroundDispatcher: DispatchersProvider,
) : ICoinLocalDataSource {

    private val db: CryptoCoin = CryptoCoin(sqlDriver)

    override suspend fun put(categoryId: String, coin: CoinDbModel) {
        db.transactionWithContext(backgroundDispatcher.io()) {
            with(coin) {
                db.coinQueries.insert(
                    id = id,
                    categoryId = categoryId,
                    img = img,
                    name = name,
                    currentCost = currentCost,
                    lastChangePercent = lastChangePercent,
                    cap = cap,
                    description = description,
                )
            }
        }
    }

    override fun get(id: String): Flow<CoinDbModel> =
        db.coinQueries.selectById(id, ::toDbModel)
            .asFlow()
            .mapToOne(backgroundDispatcher.io())

    override fun getAll(): Flow<List<CoinDbModel>> =
        db.coinQueries.selectAll(::toDbModel)
            .asFlow()
            .mapToList(backgroundDispatcher.io())

    override suspend fun deleteAll() {
        db.coinQueries.deleteAll()
    }
}