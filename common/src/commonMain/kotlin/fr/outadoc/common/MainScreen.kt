package fr.outadoc.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
                Column {
                    Text("Jour n°%d".format(state.dayStats.dayNumber))
                    Text("%d personnes ont trouvé le mot d'aujourd'hui.".format(state.dayStats.solverCount))
                }
            }
        }

        item(key = "win") {
            if (state.winningWord != null) {
                Column {
                    Text("Bravo !")
                    Text("Le mot du jour était ${state.winningWord}.")
                }
            }
        }

        item(key = "input") {
            Column {
                TextField(
                    value = state.currentInputWord,
                    label = { Text("Devinez le mot secret") },
                    onValueChange = onInputChanged,
                    enabled = !state.isLoading,
                    isError = state.errorMessage != null
                )

                if (state.errorMessage != null) {
                    Text(
                        text = state.errorMessage,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                Button(
                    onClick = onGuessWordClicked,
                    enabled = !state.isLoading && state.currentInputWord.isNotBlank()
                ) {
                    Text("Deviner")
                }
            }
        }

        items(state.guessedWords, key = { score -> score.word }) { score ->
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text(score.word)
                Text("%.2f".format(score.score))
                Text("%d".format(score.percentile))
            }
        }
    }
}
