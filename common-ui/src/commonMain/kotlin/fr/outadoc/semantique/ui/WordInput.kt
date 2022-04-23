package fr.outadoc.semantique.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import fr.outadoc.semantique.ui.strings.stringResource
import fr.outadoc.semantique.ui.util.AnimatedNullability

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
                label = { Text(stringResource(MR.strings.input_prompt)) },
                onValueChange = onInputChanged,
                readOnly = isLoading,
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
                        contentDescription = stringResource(MR.strings.input_submit)
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
