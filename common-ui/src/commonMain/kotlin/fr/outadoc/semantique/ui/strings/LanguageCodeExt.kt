package fr.outadoc.semantique.ui.strings

import dev.icerock.moko.resources.desc.StringDesc

fun setLocale(languageCode: String) {
    StringDesc.localeType = StringDesc.LocaleType.Custom(languageCode)
}
