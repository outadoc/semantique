package fr.outadoc.semantique.ui.strings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.icerock.moko.resources.PluralsResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Plural
import dev.icerock.moko.resources.desc.PluralFormatted
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.ResourceFormatted
import dev.icerock.moko.resources.desc.StringDesc

@Composable
actual fun stringResource(resource: StringResource): String {
    val languageCode = LocalLanguageCode.current
    return remember(languageCode, resource) {
        setLocale(languageCode)
        StringDesc.Resource(resource).localized()
    }
}

@Composable
actual fun stringResource(resource: StringResource, vararg args: Any): String {
    val languageCode = LocalLanguageCode.current
    return remember(languageCode, resource, args) {
        setLocale(languageCode)
        StringDesc.ResourceFormatted(resource, *args).localized()
    }
}

@Composable
actual fun stringResource(resource: PluralsResource, quantity: Int): String {
    val languageCode = LocalLanguageCode.current
    return remember(languageCode, resource, quantity) {
        setLocale(languageCode)
        StringDesc.Plural(resource, quantity).localized()
    }
}

@Composable
actual fun stringResource(resource: PluralsResource, quantity: Int, vararg args: Any): String {
    val languageCode = LocalLanguageCode.current
    return remember(languageCode, resource, quantity, args) {
        setLocale(languageCode)
        StringDesc.PluralFormatted(resource, quantity, *args).localized()
    }
}