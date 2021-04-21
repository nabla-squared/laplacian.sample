package laplacian.sample.service.word_list_api.graphql.type_resolver.rest_api_sample.word

/**
 * A data class for a word record.
 */
data class Word(
    /**
     * The lemma of this word.
     */
    val lemma: String,
    /**
     * The rank of this word.
     */
    val rank: Int,
    /**
     * The frequency of this word.
     */
    val frequency: Int,
    /**
     * The japanese of this word.
     */
    val japanese: String,
    /**
     * The commentary of this word.
     */
    val commentary: String,
) {
    companion object {
        /**
         * Creates a word instance from the given map.
         */
        @JvmStatic
        fun from(record: Map<String, Any?>): Word = Word(
            lemma = record["lemma"] as String,
            rank = record["rank"] as Int,
            frequency = record["frequency"] as Int,
            japanese = record["japanese"] as String,
            commentary = record["commentary"] as String,
        )

        /**
         * Creates a set of word instances from the given records.
         */
        @JvmStatic
        fun from(records: List<Map<String, Any?>>): List<Word> = records.map { from(it) }
    }
}
