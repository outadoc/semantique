package fr.outadoc.semantique.api.cemantix

class CemantixInvalidTargetWordException(word: String) :
    Exception("$word n'est pas le mot attendu, impossible de récupérer les mots voisins.")
