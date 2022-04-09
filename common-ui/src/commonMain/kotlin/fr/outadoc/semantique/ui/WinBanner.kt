package fr.outadoc.semantique.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import fr.outadoc.semantique.model.Word

@Composable
fun WinBanner(modifier: Modifier = Modifier, winningWord: Word) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Bravo ! \uD83C\uDF89",
                style = MaterialTheme.typography.h5
            )

            Text(
                buildAnnotatedString {
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
        }
    }
}
