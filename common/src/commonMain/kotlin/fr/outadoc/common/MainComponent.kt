package fr.outadoc.common

import com.arkivanov.decompose.ComponentContext
import fr.outadoc.semantique.api.model.DayStats
import fr.outadoc.semantique.api.model.WordScore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainComponent(componentContext: ComponentContext) : ComponentContext by componentContext {

    sealed class State {

        object Initializing : State()

        data class Playing(
            val isLoading: Boolean = false,
            val currentInputWord: String = "",
            val dayStats: DayStats,
            val guessedWords: List<WordScore>,
            val lastAttempt: WordScore?,
            val winningWord: WordScore?
        ) : State()
    }

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Initializing)
    val state = _state.asStateFlow()

    fun initialize() {
    }

    fun onInputChanged(word: String) {

    }

    fun onGuessWordClicked() {
    }
}
