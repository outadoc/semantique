package fr.outadoc.semantique.ui

import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    onGuessWordClicked: () -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp)
    ) {
        stickyHeader(key = "header") {
            Header(
                modifier = Modifier.padding(bottom = 16.dp),
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
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(),
                    winningWord = winningWord
                )
            }
        }

        item("lastAttempt") {
            AnimatedNullability(
                state.latestAttempt,
                enter = fadeIn() + expandHorizontally(),
                exit = fadeOut() + shrinkHorizontally()
            ) { lastAttempt ->
                Card(modifier = Modifier.padding(bottom = 16.dp)) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.History,
                            contentDescription = "Last attempt"
                        )

                        WordScoreRow(
                            modifier = Modifier.padding(start = 8.dp),
                            score = lastAttempt,
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }

        itemsIndexed(state.guessedWords, key = { _, score -> score.word }) { index, score ->
            WordScoreRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                score = score,
                style = MaterialTheme.typography.body2,
                emphasize = score == state.latestAttempt
            )

            if (index < state.guessedWords.lastIndex) {
                Divider(modifier = Modifier.padding(vertical = 6.dp))
            }
        }
    }
}
