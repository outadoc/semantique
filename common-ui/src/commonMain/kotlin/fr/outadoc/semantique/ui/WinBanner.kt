package fr.outadoc.semantique.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
                text = "Bravo ! \uD83C\uDF89",
                style = MaterialTheme.typography.h5
            )

            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = buildAnnotatedString {
                    append("Vous avez trouvé le mot du jour, ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(winningWord.word)
                    }
                    append(", en ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("${winningWord.attemptNumber} coup(s) !")
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
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Switch(
                    modifier = Modifier.padding(end = 8.dp),
                    checked = displayNeighbors,
                    onCheckedChange = null
                )

                Text("Voir les mots les plus proches")
            }
        }
    }
}
