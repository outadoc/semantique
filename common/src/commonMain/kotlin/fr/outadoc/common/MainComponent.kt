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
        val errorMessage: String? = null,
        val dayStats: DayStats? = null,
        val guessedWords: List<WordScore> = emptyList(),
        val lastAttempt: WordScore? = null,
        val winningWord: WordScore? = null
    )

    private val _state: MutableStateFlow<State> = MutableStateFlow(State(isLoading = true))
    val state = _state.asStateFlow()

    fun initialize() {
        scope.launch {
            val stats = try {
                api.getDayStats()
            } catch (e: Exception) {
                null
            }

            _state.emit(
                State(
                    isLoading = false,
                    dayStats = stats
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

            try {
                val score = api.getScore(currentState.currentInputWord)

                val guessedWords = (currentState.guessedWords + score).sortedByDescending { word -> word.score }

                _state.emit(
                    currentState.copy(
                        isLoading = false,
                        currentInputWord = "",
                        errorMessage = null,
                        dayStats = score.dayStats,
                        guessedWords = guessedWords,
                        lastAttempt = score,
                        winningWord = guessedWords.firstOrNull { word -> word.score == WINNING_SCORE }
                    )
                )
            } catch (e: Exception) {
                _state.emit(
                    currentState.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
                )
            }
        }
    }

    companion object {
        const val WINNING_SCORE = 1.0f
    }
}
