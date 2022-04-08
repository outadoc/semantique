package fr.outadoc.semantique.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fr.outadoc.semantique.viewmodels.Word

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
                    .weight(0.5f, fill = true)
                    .alignByBaseline(),
                text = score.word,
                style = style,
                fontWeight = fontWeight
            )

            Text(
                modifier = Modifier
                    .weight(0.2f)
                    .alignByBaseline(),
                text = "%.2f".format(score.score),
                textAlign = TextAlign.End,
                style = style,
                fontWeight = fontWeight
            )

            Box(modifier = Modifier.weight(0.6f)) {
                score.percentile?.let { percentile ->
                    LinearProgressIndicator(
                        modifier = Modifier
                            .height(10.dp)
                            .fillMaxWidth(),
                        progress = percentile / 1000f
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
