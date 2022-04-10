package fr.outadoc.semantique.viewmodels

import fr.outadoc.semantique.api.SemanticApi
import fr.outadoc.semantique.api.model.DayStats
import fr.outadoc.semantique.model.Word
import fr.outadoc.semantique.storage.Storage
import fr.outadoc.semantique.storage.schema.Attempt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(
    private val scope: CoroutineScope,
    private val api: SemanticApi,
    private val storage: Storage
) {
    data class State(
        val isLoading: Boolean,
        val languageCode: String = Locale.FRENCH.language,
        val currentInputWord: String = "",
        val errorMessage: String? = null,
        val dayStats: DayStats? = null,
        val guessedWords: List<Word> = emptyList(),
        val neighbors: List<Word>? = null,
        val latestAttempt: Word? = null,
        val winningWord: Word? = null,
        val displayNeighbors: Boolean = false
    ) {
        val displayedWords: List<Word> =
            neighbors
                .takeIf { displayNeighbors }
                .orEmpty()
                .filterNot { neighbor ->
                    guessedWords.any { guess -> guess.word == neighbor.word }
                }
                .plus(guessedWords)
                .sortedByDescending { it.score }
    }

    private val _state: MutableStateFlow<State> = MutableStateFlow(State(isLoading = true))
    val state = _state.asStateFlow()

    fun onStart() {
        val currentState = _state.value
        scope.launch {
            initializeDay(
                languageCode = currentState.languageCode
            )
        }
    }

    fun switchLanguage(languageCode: String) {
        scope.launch {
            initializeDay(
                languageCode = languageCode
            )
        }
    }

    private suspend fun initializeDay(languageCode: String) {
        try {
            val stats = api.getDayStats(languageCode = languageCode)
            val previousAttempts = storage.getAttemptsForDay(stats.dayNumber)

            val guessedWords = previousAttempts
                .map { attempt ->
                    Word(
                        attemptNumber = attempt.attemptNumber,
                        word = attempt.word,
                        score = attempt.score,
                        percentile = attempt.percentile
                    )
                }

            val winningWord = guessedWords.findWinningWordOrNull()

            _state.emit(
                State(
                    isLoading = false,
                    dayStats = stats,
                    guessedWords = guessedWords,
                    neighbors = getNeighbors(
                        languageCode = languageCode,
                        winningWord = winningWord
                    ),
                    winningWord = winningWord
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
                    val score = api.getScore(
                        languageCode = currentState.languageCode,
                        word = inputWord
                    )

                    val lastAttemptNumber =
                        currentState.guessedWords.maxOfOrNull { word ->
                            word.attemptNumber ?: 0
                        }

                    val attemptNumber = (lastAttemptNumber ?: 0) + 1

                    val word = Word(
                        attemptNumber = attemptNumber,
                        word = score.word,
                        score = score.score,
                        percentile = score.percentile
                    )

                    val guessedWords = (currentState.guessedWords + word)

                    val currentDayNumber = currentState.dayStats?.dayNumber
                    val guessedDayNumber = score.dayStats?.dayNumber

                    guessedDayNumber?.let { dayNumber ->
                        storage.addAttempt(
                            Attempt(
                                languageCode = currentState.languageCode,
                                dayNumber = dayNumber,
                                attemptNumber = attemptNumber,
                                word = score.word,
                                score = score.score,
                                percentile = score.percentile
                            )
                        )
                    }

                    val winningWord = guessedWords.findWinningWordOrNull()

                    _state.emit(
                        currentState.copy(
                            isLoading = false,
                            currentInputWord = "",
                            errorMessage = null,
                            dayStats = score.dayStats,
                            guessedWords = guessedWords,
                            latestAttempt = word,
                            winningWord = winningWord,
                            neighbors = currentState.neighbors ?: getNeighbors(
                                languageCode = currentState.languageCode,
                                winningWord = winningWord
                            )
                        )
                    )

                    if (currentDayNumber != null && guessedDayNumber != null
                        && guessedDayNumber > currentDayNumber
                    ) {
                        // New day, new game!
                        initializeDay(
                            languageCode = currentState.languageCode
                        )
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

    fun onDisplayNeighborsToggled(displayNeighbors: Boolean) {
        val currentState = _state.value
        if (currentState.neighbors == null) return

        scope.launch {
            _state.emit(
                currentState.copy(
                    displayNeighbors = displayNeighbors
                )
            )
        }
    }

    private suspend fun getNeighbors(languageCode: String, winningWord: Word?): List<Word>? =
        if (winningWord == null) null
        else api.getNearby(
            languageCode = languageCode,
            word = winningWord.word
        ).map { neighbor ->
            Word(
                attemptNumber = null,
                word = neighbor.word,
                score = neighbor.score,
                percentile = neighbor.percentile
            )
        }

    private fun List<Word>.findWinningWordOrNull(): Word? =
        firstOrNull { it.score == 1.0f }
}
