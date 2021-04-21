package laplacian.sample.service.word_list_api.graphql.type_resolver.rest_api_sample.word_list

/**
 * A data class for a word_list record.
 */
data class WordList(
    /**
     * The name of this word_list.
     */
    val name: String,
    val content: List<WordListEntry>,
) {
    companion object {
        /**
         * Creates a word_list instance from the given map.
         */
        @JvmStatic
        fun from(record: Map<String, Any?>): WordList = WordList(
            name = record["name"] as String,
            content = ((record["content"] ?: emptyList<Map<String, Any?>>()) as List<Map<String, Any?>>).map{ WordListEntry.from(it) },
        )

        /**
         * Creates a set of word_list instances from the given records.
         */
        @JvmStatic
        fun from(records: List<Map<String, Any?>>): List<WordList> = records.map { from(it) }
    }
}
