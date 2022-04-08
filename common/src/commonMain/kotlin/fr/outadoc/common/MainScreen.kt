package fr.outadoc.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import fr.outadoc.semantique.api.model.DayStats
import fr.outadoc.semantique.api.model.WordScore

@Composable
fun MainScreen(mainComponent: MainComponent) {
    val state by mainComponent.state.collectAsState()

    LaunchedEffect("initial") {
        mainComponent.initialize()
    }

    MainList(
        state,
        onInputChanged = mainComponent::onInputChanged,
        onGuessWordClicked = mainComponent::onGuessWordClicked
    )
}

@Composable
fun MainList(state: MainComponent.State, onInputChanged: (String) -> Unit, onGuessWordClicked: () -> Unit) {
    if (state.isLoading) {
        CircularProgressIndicator()
    }

    LazyColumn(contentPadding = PaddingValues(8.dp)) {
        item(key = "dayStats") {
            if (state.dayStats != null) {
                StatsHeader(dayStats = state.dayStats)
            }
        }

        item(key = "win") {
            if (state.winningWord != null) {
                WinBanner(winningWord = state.winningWord)
            }
        }

        item(key = "input") {
            WordInput(
                currentInputWord = state.currentInputWord,
                isLoading = state.isLoading,
                errorMessage = state.errorMessage,
                onInputChanged = onInputChanged,
                onGuessWordClicked = onGuessWordClicked,
            )
        }

        item("lastAttempt") {
            if (state.lastAttempt != null) {
                WordScoreRow(score = state.lastAttempt)
                Divider()
            }
        }

        items(state.guessedWords, key = { score -> score.word }) { score ->
            WordScoreRow(score = score)
        }
    }
}

@Composable
fun StatsHeader(modifier: Modifier = Modifier, dayStats: DayStats) {
    Column(modifier = modifier) {
        Text("Jour n°%d".format(dayStats.dayNumber))
        Text("%d personnes ont trouvé le mot d'aujourd'hui.".format(dayStats.solverCount))
    }
}

@Composable
fun WinBanner(modifier: Modifier = Modifier, winningWord: WordScore) {
    Column(modifier = modifier) {
        Text("Bravo !")
        Text("Le mot du jour était ${winningWord.word}.")
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WordInput(
    modifier: Modifier = Modifier,
    currentInputWord: String,
    onInputChanged: (String) -> Unit,
    onGuessWordClicked: () -> Unit,
    isLoading: Boolean,
    errorMessage: String?
) {
    Column(modifier) {
        TextField(
            value = currentInputWord,
            label = { Text("Devinez le mot secret") },
            onValueChange = onInputChanged,
            enabled = !isLoading,
            isError = errorMessage != null,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions {
                onGuessWordClicked()
            },
            modifier = Modifier.onPreviewKeyEvent { event ->
                if (event.key == Key.Enter && event.type == KeyEventType.KeyUp) {
                    onGuessWordClicked()
                    true
                } else false
            }
        )

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Button(
            onClick = onGuessWordClicked,
            enabled = !isLoading && currentInputWord.isNotBlank()
        ) {
            Text("Deviner")
        }
    }
}

@Composable
fun WordScoreRow(modifier: Modifier = Modifier, score: WordScore) {
    Row(modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        Text(score.word)
        Text("%.2f".format(score.score))
        Text("%d".format(score.percentile))
    }
}
