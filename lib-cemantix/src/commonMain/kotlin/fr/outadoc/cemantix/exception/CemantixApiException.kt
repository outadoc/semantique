package fr.outadoc.cemantix.exception

import fr.outadoc.cemantix.stripTags

class CemantixApiException(htmlMessage: String) : Exception(htmlMessage.stripTags())
