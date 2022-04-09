package fr.outadoc.semantique.viewmodels

import fr.outadoc.semantique.api.SemanticApi
import fr.outadoc.semantique.api.model.DayStats
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val scope: CoroutineScope,
    private val api: SemanticApi
) {
    data class State(
        val isLoading: Boolean,
        val currentInputWord: String = "",
        val errorMessage: String? = null,
        val dayStats: DayStats? = null,
        val guessedWords: List<Word> = emptyList(),
        val latestAttempt: Word? = null,
        val winningWord: Word? = null
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

                val word = score.toWord(
                    attemptNumber = (currentState.guessedWords.maxOfOrNull { word -> word.attemptNumber } ?: 0) + 1
                )

                val alreadyGuessed: Boolean =
                    currentState.guessedWords.any { existingScore ->
                        existingScore.word == score.word
                    }


                val guessedWords: List<Word> =
                    if (alreadyGuessed) currentState.guessedWords
                    else (currentState.guessedWords + word).sortedByDescending { it.score }

                _state.emit(
                    currentState.copy(
                        isLoading = false,
                        currentInputWord = "",
                        errorMessage = null,
                        dayStats = score.dayStats,
                        guessedWords = guessedWords,
                        latestAttempt = word,
                        winningWord = guessedWords.firstOrNull { it.score == WINNING_SCORE }
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
