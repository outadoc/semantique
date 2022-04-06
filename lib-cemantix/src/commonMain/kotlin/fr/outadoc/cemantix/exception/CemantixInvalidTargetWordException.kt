package fr.outadoc.cemantix.exception

class CemantixInvalidTargetWordException(word: String) :
    Exception("$word n'est pas le mot attendu, impossible de récupérer les mots voisins.")
