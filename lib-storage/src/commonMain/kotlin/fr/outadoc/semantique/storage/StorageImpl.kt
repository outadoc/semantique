package fr.outadoc.semantique.storage

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import fr.outadoc.semantique.storage.db.SemantiqueDatabase
import fr.outadoc.semantique.storage.schema.Attempt
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class StorageImpl(
    private val database: SemantiqueDatabase,
    private val ioDispatcher: CoroutineDispatcher
) : Storage {

    override suspend fun getAttemptsForDay(languageCode: String, dayNumber: Long): List<Attempt> =
        withContext(ioDispatcher) {
            database.attemptsQueries
                .getAllAttemptsForDay(
                    languageCode = languageCode,
                    dayNumber = dayNumber
                )
                .asFlow()
                .mapToList()
                .first()
        }

    override suspend fun addAttempt(attempt: Attempt) =
        withContext(ioDispatcher) {
            database.attemptsQueries.insertAttempt(attempt)
        }
}
