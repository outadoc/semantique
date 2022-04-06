package fr.outadoc.semantique.api.model

data class WordScore(

    /**
     * The word which these stats refer to.
     */
    val word: String,

    /**
     * Score of the word compared to the target word.
     *
     * Included in the range `[-1.0, 1.0]`.
     */
    val score: Float,

    /**
     * Rank of the word in the list of the nearest 1000 words to the target word.
     *
     * Included in the range `[0, 1000]`. Null if not in the top 1000.
     */
    val percentile: Int?,

    /**
     * Up-to-date stats about the current day.
     */
    val dayStats: DayStats? = null
)
