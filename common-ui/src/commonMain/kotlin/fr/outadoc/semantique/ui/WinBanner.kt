package fr.outadoc.semantique.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import fr.outadoc.semantique.model.Word
import fr.outadoc.semantique.ui.strings.stringResource

@Composable
fun WinBanner(
    modifier: Modifier = Modifier,
    winningWord: Word,
    displayNeighbors: Boolean,
    onDisplayNeighborsChanged: (Boolean) -> Unit
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 8.dp
            )
        ) {
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = stringResource(MR.strings.win_congrats),
                style = MaterialTheme.typography.h5
            )

            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = buildAnnotatedString {
                    append(stringResource(MR.strings.win_title1))
                    append(" ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(winningWord.word)
                    }
                    append(stringResource(MR.strings.win_title2))
                    append(" ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(
                            stringResource(
                                MR.strings.win_title3,
                                winningWord.attemptNumber ?: 0
                            )
                        )
                    }
                },
                style = MaterialTheme.typography.subtitle1
            )

            Row(
                modifier = Modifier
                    .toggleable(
                        value = displayNeighbors,
                        role = Role.Checkbox,
                        onValueChange = onDisplayNeighborsChanged
                    )
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Switch(
                    modifier = Modifier.padding(end = 8.dp),
                    checked = displayNeighbors,
                    onCheckedChange = null
                )

                Text(stringResource(MR.strings.win_showNeighbors))
            }
        }
    }
}
