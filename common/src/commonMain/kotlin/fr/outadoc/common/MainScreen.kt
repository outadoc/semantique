package fr.outadoc.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(mainComponent: MainComponent) {
    val state by mainComponent.state.collectAsState()
    MainList(
        state,
        onInputChanged = mainComponent::onInputChanged,
        onGuessWordClicked = mainComponent::onGuessWordClicked
    )
}

@Composable
fun MainList(state: MainComponent.State, onInputChanged: (String) -> Unit, onGuessWordClicked: () -> Unit) {
    when (state) {
        MainComponent.State.Initializing -> {
            CircularProgressIndicator()
        }
        is MainComponent.State.Playing -> {
            LazyColumn(contentPadding = PaddingValues(8.dp)) {
                item(key = "dayStats") {
                    Column {
                        Text("Jour n°%d".format(state.dayStats.dayNumber))
                        Text("%d personnes ont trouvé le mot d'aujourd'hui.".format(state.dayStats.solverCount))
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
                            onValueChange = onInputChanged
                        )

                        Button(onClick = onGuessWordClicked) {
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
    }
}
