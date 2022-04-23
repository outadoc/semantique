package fr.outadoc.semantique.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import fr.outadoc.semantique.model.Word
import fr.outadoc.semantique.ui.strings.stringResource

@Composable
fun WordScoreRow(
    modifier: Modifier = Modifier,
    score: Word,
    style: TextStyle,
    emphasize: Boolean = false
) {
    val fontWeight = if (emphasize) FontWeight.Bold else FontWeight.Normal

    Column(modifier = modifier.fillMaxWidth()) {
        RowLine(
            column1 = { modifier ->
                Box(modifier = modifier.alignByBaseline()) {
                    score.attemptNumber?.let { attemptNumber ->
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .alpha(ContentAlpha.medium),
                            text = stringResource(MR.strings.row_attemptNumber, attemptNumber),
                            textAlign = TextAlign.End,
                            style = style,
                            fontWeight = fontWeight
                        )
                    }
                }
            },
            column2 = { modifier ->
                Text(
                    modifier = modifier
                        .alignByBaseline()
                        .fillMaxWidth(),
                    text = score.word,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = style,
                    fontWeight = fontWeight
                )
            },
            column3 = { modifier ->
                Text(
                    modifier = modifier
                        .alignByBaseline()
                        .alpha(ContentAlpha.medium),
                    text = stringResource(MR.strings.row_score, score.score * 100),
                    textAlign = TextAlign.End,
                    style = style,
                    fontWeight = fontWeight
                )
            }
        )

        val percentile = score.percentile
        if (percentile != null) {
            RowLine(
                column2 = { modifier ->
                    val progress = remember { Animatable(0f) }
                    LaunchedEffect(score) {
                        progress.animateTo(
                            targetValue = percentile / 1_000f,
                            animationSpec = tween(
                                durationMillis = 1_000,
                                delayMillis = 100,
                                easing = LinearEasing
                            )
                        )
                    }

                    // Don't display progress if part of the neighbors list
                    if (score.attemptNumber != null) {
                        LinearProgressIndicator(
                            modifier = modifier
                                .align(Alignment.CenterVertically)
                                .height(8.dp)
                                .fillMaxWidth(),
                            progress = progress.value
                        )
                    }
                },
                column3 = { modifier ->
                    Text(
                        modifier = modifier
                            .alignByBaseline()
                            .fillMaxWidth(),
                        text = stringResource(MR.strings.row_percentile, percentile),
                        textAlign = TextAlign.End,
                        style = style,
                        fontWeight = fontWeight
                    )
                }
            )
        }
    }
}

@Composable
private fun RowLine(
    modifier: Modifier = Modifier,
    column1: @Composable RowScope.(Modifier) -> Unit = { Box(modifier = it) },
    column2: @Composable RowScope.(Modifier) -> Unit = { Box(modifier = it) },
    column3: @Composable RowScope.(Modifier) -> Unit = { Box(modifier = it) }
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        column1(Modifier.weight(0.1f))
        column2(Modifier.weight(0.65f))
        column3(Modifier.weight(0.25f))
    }
}
