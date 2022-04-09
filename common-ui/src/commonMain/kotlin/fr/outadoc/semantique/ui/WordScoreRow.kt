package fr.outadoc.semantique.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fr.outadoc.semantique.model.Word

@Composable
fun WordScoreRow(
    modifier: Modifier = Modifier,
    score: Word,
    style: TextStyle,
    emphasize: Boolean = false
) {
    val fontWeight = if (emphasize) FontWeight.Bold else FontWeight.Normal
    SelectionContainer {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(0.1f)
                    .alignByBaseline(),
                text = "%,d".format(score.attemptNumber),
                textAlign = TextAlign.End,
                style = style,
                fontWeight = fontWeight
            )

            Text(
                modifier = Modifier
                    .weight(0.4f, fill = true)
                    .alignByBaseline(),
                text = score.word,
                style = style,
                fontWeight = fontWeight
            )

            Text(
                modifier = Modifier
                    .weight(0.2f)
                    .alignByBaseline(),
                text = "%.2f".format(score.score * 100),
                textAlign = TextAlign.End,
                style = style,
                fontWeight = fontWeight
            )

            Box(modifier = Modifier.weight(0.5f)) {
                score.percentile?.let { percentile ->
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

                    LinearProgressIndicator(
                        modifier = Modifier
                            .height(10.dp)
                            .fillMaxWidth(),
                        progress = progress.value
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(0.2f)
                    .alignByBaseline()
            ) {
                score.percentile?.let { percentile ->
                    Text(
                        text = "%d".format(percentile),
                        textAlign = TextAlign.End,
                        style = style,
                        fontWeight = fontWeight
                    )
                }
            }
        }
    }
}
