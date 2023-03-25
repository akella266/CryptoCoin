package ru.akella.cryptocoin

import ru.akella.cryptocoin.db.Breed
import ru.akella.cryptocoin.db.CryptoCoin
import ru.akella.cryptocoin.sqldelight.transactionWithContext
import co.touchlab.kermit.Logger
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class DatabaseHelper(
    sqlDriver: SqlDriver,
    private val log: Logger,
    private val backgroundDispatcher: CoroutineDispatcher
) {
    private val dbRef: CryptoCoin = CryptoCoin(sqlDriver)

    fun selectAllItems(): Flow<List<Breed>> =
        dbRef.tableQueries
            .selectAll()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .flowOn(backgroundDispatcher)

    suspend fun insertBreeds(breeds: List<String>) {
        log.d { "Inserting ${breeds.size} breeds into database" }
        dbRef.transactionWithContext(backgroundDispatcher) {
            breeds.forEach { breed ->
                dbRef.tableQueries.insertBreed(breed)
            }
        }
    }

    fun selectById(id: Long): Flow<List<Breed>> =
        dbRef.tableQueries
            .selectById(id)
            .asFlow()
            .mapToList(Dispatchers.Default)
            .flowOn(backgroundDispatcher)

    suspend fun deleteAll() {
        log.i { "Database Cleared" }
        dbRef.transactionWithContext(backgroundDispatcher) {
            dbRef.tableQueries.deleteAll()
        }
    }

    suspend fun updateFavorite(breedId: Long, favorite: Boolean) {
        log.i { "Breed $breedId: Favorited $favorite" }
        dbRef.transactionWithContext(backgroundDispatcher) {
            dbRef.tableQueries.updateFavorite(favorite, breedId)
        }
    }
}
