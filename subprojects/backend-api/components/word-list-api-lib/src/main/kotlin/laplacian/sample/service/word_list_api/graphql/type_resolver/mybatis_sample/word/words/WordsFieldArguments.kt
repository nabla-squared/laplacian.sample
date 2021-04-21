package laplacian.sample.service.word_list_api.graphql.type_resolver.mybatis_sample.word.words

import laplacian.sample.service.word_list_api.graphql.type_resolver.mybatis_sample.word.*

/**
 * A data class for input arguments of words field.
 */
data class WordsFieldArguments (
    val lemmas: List<String> = emptyList(),
) {
    companion object {
        @JvmStatic
        fun from(args: Map<String, Any?>): WordsFieldArguments {
            return WordsFieldArguments(
                lemmas = args["lemmas"] as? List<String> ?: emptyList(),
            )
        }
    }
}
