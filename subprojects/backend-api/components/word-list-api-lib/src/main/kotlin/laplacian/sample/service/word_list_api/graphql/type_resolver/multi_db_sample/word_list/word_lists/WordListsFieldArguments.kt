package laplacian.sample.service.word_list_api.graphql.type_resolver.multi_db_sample.word_list.word_lists

import laplacian.sample.service.word_list_api.graphql.type_resolver.multi_db_sample.word_list.*

/**
 * A data class for input arguments of word_lists field.
 */
data class WordListsFieldArguments (
    val names: List<String> = emptyList(),
) {
    companion object {
        @JvmStatic
        fun from(args: Map<String, Any?>): WordListsFieldArguments {
            return WordListsFieldArguments(
                names = args["names"] as? List<String> ?: emptyList(),
            )
        }
    }
}
