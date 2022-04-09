package fr.outadoc.semantique.storage

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import fr.outadoc.semantique.storage.db.SemantiqueDatabase
import fr.outadoc.semantique.storage.schema.Attempt
import kotlinx.coroutines.flow.first

class StorageImpl(private val database: SemantiqueDatabase) : Storage {

    override suspend fun getAttemptsForDay(dayNumber: Long): List<Attempt> {
        return database.attemptsQueries
            .getAllAttemptsForDay(dayNumber)
            .asFlow()
            .mapToList()
            .first()
    }

    override suspend fun addAttempt(attempt: Attempt) {
        database.attemptsQueries.transaction {
            database.attemptsQueries.insertAttempt(attempt)
        }
    }
}
