package fr.outadoc.semantique.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import java.util.*

@Composable
fun LanguagePicker(
    currentLanguageCode: String,
    onLanguageCodeSelected: (String) -> Unit,
    availableLanguageCodes: List<String>
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = !expanded }) {
            Text(text = currentLanguageCode.parseLanguageCode().emoji)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            availableLanguageCodes
                .map { code -> code.parseLanguageCode() }
                .forEach { language ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onLanguageCodeSelected(language.languageCode)
                        }
                    ) {
                        Text(text = "${language.emoji}  ${language.label}")
                    }
                }
        }
    }
}

private data class Language(
    val languageCode: String,
    val emoji: String,
    val label: String
)

private fun String.parseLanguageCode() = when (this) {
    Locale.FRENCH.language -> Language(
        languageCode = this,
        emoji = "\uD83C\uDDEB\uD83C\uDDF7",
        label = "Français"
    )
    Locale.ENGLISH.language -> Language(
        languageCode = this,
        emoji = "\uD83C\uDDFA\uD83C\uDDF8",
        label = "English"
    )
    else -> error("Unsupported language code: $this")
}
