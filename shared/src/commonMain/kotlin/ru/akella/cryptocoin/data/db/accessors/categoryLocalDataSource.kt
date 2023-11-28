package ru.akella.cryptocoin.data.db.accessors

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import ru.akella.cryptocoin.data.db.mappers.toDbModel
import ru.akella.cryptocoin.data.db.models.CategoryDbModel
import ru.akella.cryptocoin.data.db.transactionWithContext
import ru.akella.cryptocoin.db.CryptoCoin

interface ICategoryLocalDataSource {

    suspend fun put(category: CategoryDbModel)

    fun get(id: String): Flow<CategoryDbModel>

    fun getAll(): Flow<List<CategoryDbModel>>

    suspend fun deleteAll()
}

internal class CategoryLocalDataSource(
    sqlDriver: SqlDriver,
    private val backgroundDispatcher: CoroutineDispatcher,
) : ICategoryLocalDataSource {

    private val db: CryptoCoin = CryptoCoin(sqlDriver)

    override suspend fun put(category: CategoryDbModel) {
        db.transactionWithContext(backgroundDispatcher) {
            with(category) {
                db.categoryQueries.insert(
                    id,
                    description,
                    tradingVolume,
                    cap
                )
            }
        }
    }

    override fun get(id: String): Flow<CategoryDbModel> =
        db.categoryQueries.selectById(id, ::toDbModel)
            .asFlow()
            .mapToOne(backgroundDispatcher)

    override fun getAll(): Flow<List<CategoryDbModel>> =
        db.categoryQueries.selectAll(::toDbModel)
            .asFlow()
            .mapToList(backgroundDispatcher)

    override suspend fun deleteAll() {
        db.categoryQueries.deleteAll()
    }
}
