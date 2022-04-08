package fr.outadoc.semantique.ui

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import fr.outadoc.semantique.api.model.DayStats
import fr.outadoc.semantique.api.model.WordScore
import fr.outadoc.semantique.ui.util.AnimatedNullability
import fr.outadoc.semantique.viewmodels.MainViewModel

@Composable
fun MainScreen(modifier: Modifier = Modifier, mainViewModel: MainViewModel) {
    val state by mainViewModel.state.collectAsState()

    LaunchedEffect("initial") {
        mainViewModel.initialize()
    }

    MainList(
        modifier = modifier,
        state = state,
        onInputChanged = mainViewModel::onInputChanged,
        onGuessWordClicked = mainViewModel::onGuessWordClicked
    )
}

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
                state.lastAttempt,
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
                style = MaterialTheme.typography.body2
            )

            if (index < state.guessedWords.lastIndex) {
                Divider(modifier = Modifier.padding(vertical = 6.dp))
            }
        }
    }
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    dayStats: DayStats?,
    isLoading: Boolean,
    currentInputWord: String,
    errorMessage: String?,
    onInputChanged: (String) -> Unit,
    onGuessWordClicked: () -> Unit
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            AnimatedNullability(
                dayStats,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) { dayStats ->
                StatsHeader(
                    modifier = Modifier.padding(bottom = 16.dp),
                    dayStats = dayStats
                )
            }

            WordInput(
                currentInputWord = currentInputWord,
                isLoading = isLoading,
                errorMessage = errorMessage,
                onInputChanged = onInputChanged,
                onGuessWordClicked = onGuessWordClicked,
            )
        }
    }
}

@Composable
fun StatsHeader(modifier: Modifier = Modifier, dayStats: DayStats) {
    Column(modifier = modifier) {
        Text(
            "Jour n°%d".format(dayStats.dayNumber),
            style = MaterialTheme.typography.h4
        )

        Text(
            "%,d personnes ont trouvé le mot du jour.".format(dayStats.solverCount),
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Composable
fun WinBanner(modifier: Modifier = Modifier, winningWord: WordScore) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Bravo ! \uD83C\uDF89",
                style = MaterialTheme.typography.h5
            )

            Text(
                buildAnnotatedString {
                    append("Le mot du jour était ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(winningWord.word)
                    }
                    append(".")
                },
                style = MaterialTheme.typography.subtitle1
            )
        }
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
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                modifier = Modifier
                    .height(60.dp)
                    .weight(1f)
                    .onPreviewKeyEvent { event ->
                        if (event.key == Key.Enter && event.type == KeyEventType.KeyUp) {
                            onGuessWordClicked()
                            true
                        } else false
                    },
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
                }
            )

            Button(
                modifier = Modifier
                    .height(60.dp)
                    .aspectRatio(1f),
                onClick = onGuessWordClicked,
                enabled = !isLoading && currentInputWord.isNotBlank()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Icon(
                        Icons.Default.Psychology,
                        contentDescription = "Deviner ce mot"
                    )
                }
            }
        }

        AnimatedNullability(errorMessage) { errorMessage ->
            Text(
                text = errorMessage,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
fun WordScoreRow(
    modifier: Modifier = Modifier,
    score: WordScore,
    style: TextStyle
) {
    SelectionContainer {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(0.5f, fill = true),
                text = score.word,
                style = style
            )

            Text(
                modifier = Modifier.weight(0.2f),
                text = "%.2f".format(score.score),
                textAlign = TextAlign.End,
                style = style
            )

            Box(modifier = Modifier.weight(1f)) {
                score.percentile?.let { percentile ->
                    LinearProgressIndicator(
                        modifier = Modifier
                            .height(10.dp)
                            .fillMaxWidth(),
                        progress = percentile / 1000f
                    )
                }
            }

            Box(modifier = Modifier.weight(0.2f)) {
                score.percentile?.let { percentile ->
                    Text(
                        text = "%d".format(percentile),
                        textAlign = TextAlign.End,
                        style = style
                    )
                }
            }
        }
    }
}
