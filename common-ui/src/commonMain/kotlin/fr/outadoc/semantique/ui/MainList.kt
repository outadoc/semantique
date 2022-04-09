package fr.outadoc.semantique.ui

import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.outadoc.semantique.ui.util.AnimatedNullability
import fr.outadoc.semantique.viewmodels.MainViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainList(
    modifier: Modifier = Modifier,
    state: MainViewModel.State,
    onInputChanged: (String) -> Unit,
    onGuessWordClicked: () -> Unit,
    onDisplayNeighborsChanged: (Boolean) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp)
    ) {
        stickyHeader(key = "header") {
            Header(
                modifier = Modifier.padding(bottom = 8.dp),
                onInputChanged = onInputChanged,
                onGuessWordClicked = onGuessWordClicked,
                dayStats = state.dayStats,
                isLoading = state.isLoading,
                currentInputWord = state.currentInputWord,
                errorMessage = state.errorMessage
            )

            AnimatedNullability(
                state.winningWord,
                enter = fadeIn() + expandHorizontally(),
                exit = fadeOut() + shrinkHorizontally()
            ) { winningWord ->
                WinBanner(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth(),
                    winningWord = winningWord,
                    displayNeighbors = state.displayNeighbors,
                    onDisplayNeighborsChanged = onDisplayNeighborsChanged
                )
            }
        }

        item("lastAttempt") {
            AnimatedNullability(
                state.latestAttempt,
                enter = fadeIn() + expandHorizontally(),
                exit = fadeOut() + shrinkHorizontally()
            ) { lastAttempt ->
                Card(modifier = Modifier.padding(bottom = 8.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Tentative précédente",
                            style = MaterialTheme.typography.subtitle2,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        WordScoreRow(
                            score = lastAttempt,
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        itemsIndexed(state.displayedWords, key = { _, score -> score.word }) { index, score ->
            WordScoreRow(
                modifier = Modifier.padding(horizontal = 8.dp),
                score = score,
                style = MaterialTheme.typography.body2,
                emphasize = score == state.latestAttempt
            )

            if (index < state.displayedWords.lastIndex) {
                Divider(modifier = Modifier.padding(vertical = 6.dp))
            }
        }
    }
}
