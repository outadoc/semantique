package fr.outadoc.semantique.ui.strings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import dev.icerock.moko.resources.PluralsResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Plural
import dev.icerock.moko.resources.desc.PluralFormatted
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.ResourceFormatted
import dev.icerock.moko.resources.desc.StringDesc

@Composable
actual fun stringResource(resource: StringResource): String {
    val context = LocalContext.current
    val languageCode = LocalLanguageCode.current
    return remember(languageCode) {
        setLocale(languageCode)
        StringDesc.Resource(resource).toString(context)
    }
}

@Composable
actual fun stringResource(resource: StringResource, vararg args: Any): String {
    val context = LocalContext.current
    val languageCode = LocalLanguageCode.current
    return remember(languageCode) {
        setLocale(languageCode)
        StringDesc.ResourceFormatted(resource, *args).toString(context)
    }
}

@Composable
actual fun stringResource(resource: PluralsResource, quantity: Int): String {
    val context = LocalContext.current
    val languageCode = LocalLanguageCode.current
    return remember(languageCode) {
        setLocale(languageCode)
        StringDesc.Plural(resource, quantity).toString(context)
    }
}

@Composable
actual fun stringResource(resource: PluralsResource, quantity: Int, vararg args: Any): String {
    val context = LocalContext.current
    val languageCode = LocalLanguageCode.current
    return remember(languageCode) {
        setLocale(languageCode)
        StringDesc.PluralFormatted(resource, quantity, *args).toString(context)
    }
}
