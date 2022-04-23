package fr.outadoc.semantique.ui.strings

import androidx.compose.runtime.compositionLocalOf

val LocalLanguageCode =
    compositionLocalOf<String> { error("Language code has not been provided.") }
