package fr.outadoc.cemantix

/**
 * Very basic function to strip simple HTML tags.
 *
 * Only opening and closing tags are supported. Do NOT use this to parse anything complex.
 */
internal fun String.stripTags(): String =
    replace(Regex("</?[a-z\\-]+>", RegexOption.IGNORE_CASE), "")
