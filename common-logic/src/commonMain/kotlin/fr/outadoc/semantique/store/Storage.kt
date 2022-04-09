package fr.outadoc.semantique.store

import fr.outadoc.semantique.model.Word

interface Storage {
    suspend fun getAttemptsForDay(dayNumber: Int): List<Word>
    suspend fun updateAttempsForDay(dayNumber: Int, words: List<Word>)
}
