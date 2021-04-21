package laplacian.sample.service.word_list_api.graphql.type_resolver.rest_api_sample.word_list

/**
 * A data class for a word_list record.
 */
data class WordListEntry(
    /**
     * The lemma of this word_list_entry.
     */
    val lemma: String,
) {
    companion object {
        /**
         * Creates a word_list instance from the given map.
         */
        @JvmStatic
        fun from(record: Map<String, Any?>): WordListEntry = WordListEntry(
            lemma = record["lemma"] as String,
        )

        /**
         * Creates a set of word_list instances from the given records.
         */
        @JvmStatic
        fun from(records: List<Map<String, Any?>>): List<WordListEntry> = records.map { from(it) }
    }
}
