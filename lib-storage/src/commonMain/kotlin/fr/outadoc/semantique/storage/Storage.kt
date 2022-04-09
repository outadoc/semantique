package fr.outadoc.semantique.storage

interface Storage {
    suspend fun getAttemptsForDay(dayNumber: Int): List<StoredAttempt>
    suspend fun updateAttempsForDay(dayNumber: Int, words: List<StoredAttempt>)
}
