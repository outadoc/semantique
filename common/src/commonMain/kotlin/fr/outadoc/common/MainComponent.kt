package fr.outadoc.common

import fr.outadoc.semantique.api.SemanticApi
import fr.outadoc.semantique.api.model.DayStats
import fr.outadoc.semantique.api.model.WordScore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainComponent(
    private val scope: CoroutineScope,
    private val api: SemanticApi
) {
    data class State(
        val isLoading: Boolean,
        val currentInputWord: String = "",
        val dayStats: DayStats? = null,
        val guessedWords: List<WordScore> = emptyList(),
        val lastAttempt: WordScore? = null,
        val winningWord: WordScore? = null
    )

    private val _state: MutableStateFlow<State> = MutableStateFlow(State(isLoading = true))
    val state = _state.asStateFlow()

    fun initialize() {
        scope.launch {
            _state.emit(
                State(
                    isLoading = false,
                    dayStats = api.getDayStats()
                )
            )
        }
    }

    fun onInputChanged(word: String) {
        val currentState = _state.value
        if (currentState.isLoading) return

        scope.launch {
            _state.emit(
                currentState.copy(currentInputWord = word)
            )
        }
    }

    fun onGuessWordClicked() {
        val currentState = _state.value
        if (currentState.isLoading) return
    
        scope.launch {
            _state.emit(
                currentState.copy(isLoading = true)
            )

            val guessedWord = currentState.currentInputWord
            val score = api.getScore(guessedWord)
            val guessedWords = currentState.guessedWords + score

            _state.emit(
                currentState.copy(
                    isLoading = false,
                    currentInputWord = "",
                    dayStats = score.dayStats,
                    guessedWords = guessedWords,
                    lastAttempt = score,
                    winningWord = guessedWords.firstOrNull { word -> word.score == WINNING_SCORE }
                )
            )
        }
    }

    companion object {
        const val WINNING_SCORE = 1.0f
    }
}
