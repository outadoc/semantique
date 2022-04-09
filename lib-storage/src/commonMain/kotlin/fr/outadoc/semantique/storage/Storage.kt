package fr.outadoc.semantique.storage

import fr.outadoc.semantique.storage.schema.Attempt

interface Storage {
    suspend fun getAttemptsForDay(dayNumber: Long): List<Attempt>
    suspend fun addAttempt(attempt: Attempt)
}
