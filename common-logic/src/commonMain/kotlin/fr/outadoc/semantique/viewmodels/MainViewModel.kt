package fr.outadoc.semantique.viewmodels

import fr.outadoc.semantique.api.SemanticApi
import fr.outadoc.semantique.api.model.DayStats
import fr.outadoc.semantique.model.Word
import fr.outadoc.semantique.model.toAttempt
import fr.outadoc.semantique.model.toWord
import fr.outadoc.semantique.storage.Storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val scope: CoroutineScope,
    private val api: SemanticApi,
    private val storage: Storage
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

    fun onStart() {
        scope.launch {
            initializeDay()
        }
    }

    private suspend fun initializeDay() {
        try {
            val stats = api.getDayStats()
            val previousAttempts = storage.getAttemptsForDay(stats.dayNumber)

            val guessedWords = previousAttempts
                .map { attempt -> attempt.toWord() }
                .sortedByDescending { it.score }

            _state.emit(
                State(
                    isLoading = false,
                    dayStats = stats,
                    guessedWords = guessedWords,
                    winningWord = guessedWords.firstOrNull { it.score == WINNING_SCORE }
                )
            )
        } catch (e: Exception) {
            _state.emit(
                State(isLoading = false)
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

        val inputWord = currentState.currentInputWord.trim()

        val alreadyGuessedWord =
            currentState.guessedWords.firstOrNull { previous ->
                previous.word == inputWord
            }

        scope.launch {
            _state.emit(
                currentState.copy(isLoading = true)
            )

            try {
                if (alreadyGuessedWord != null) {
                    _state.emit(
                        currentState.copy(
                            isLoading = false,
                            currentInputWord = "",
                            errorMessage = null,
                            latestAttempt = alreadyGuessedWord
                        )
                    )
                } else {
                    val score = api.getScore(word = inputWord)

                    val word = score.toWord(
                        attemptNumber = (currentState.guessedWords.maxOfOrNull { word -> word.attemptNumber } ?: 0) + 1
                    )

                    val guessedWords = (currentState.guessedWords + word)
                        .sortedByDescending { it.score }

                    val currentDayNumber = currentState.dayStats?.dayNumber
                    val guessedDayNumber = score.dayStats?.dayNumber

                    guessedDayNumber?.let { dayNumber ->
                        storage.addAttempt(word.toAttempt(dayNumber))
                    }

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

                    if (currentDayNumber != null && guessedDayNumber != null
                        && guessedDayNumber > currentDayNumber
                    ) {
                        // New day, new game!
                        initializeDay()
                    }
                }

            } catch (e: Exception) {
                _state.emit(
                    currentState.copy(
                        isLoading = false,
                        errorMessage = e.message,
                        currentInputWord = inputWord
                    )
                )
            }
        }
    }

    companion object {
        const val WINNING_SCORE = 1.0f
    }
}