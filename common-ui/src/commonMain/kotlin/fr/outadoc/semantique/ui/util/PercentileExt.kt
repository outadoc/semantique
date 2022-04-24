package fr.outadoc.semantique.ui.util

fun Int?.percentileToEmoji(): String =
    when (this) {
        null, 0 -> "\uD83E\uDD76"
        in 1..899 -> "\uD83D\uDE0E"
        in 900..989 -> "\uD83E\uDD75"
        in 990..998 -> "\uD83D\uDD25"
        999 -> "\uD83D\uDE31"
        else -> "\uD83E\uDD73"
    }
