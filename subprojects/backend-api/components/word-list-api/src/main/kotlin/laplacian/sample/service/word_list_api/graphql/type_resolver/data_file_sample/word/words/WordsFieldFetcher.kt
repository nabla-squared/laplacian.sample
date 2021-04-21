package laplacian.sample.service.word_list_api.graphql.type_resolver.data_file_sample.word.words

import org.springframework.stereotype.Component
import graphql.schema.DataFetchingEnvironment
import reactor.core.publisher.Mono

import laplacian.sample.service.word_list_api.graphql.type_resolver.data_file_sample.word.*
import laplacian.sample.service.word_list_api.util.*

import laplacian.sample.service.word_list_api.graphql.type_resolver.ListValueFieldFetcher
// @import_statements@
import kotlinx.coroutines.reactor.mono
import laplacian.sample.service.word_list_api.data_file.DataFileAccess
// @import_statements@
/**
 * The fetcher of the words of the word.
 */
@Component("data_file_sample.word.words.fetcher")
class WordsFieldFetcher(
    // @injections@
    val dataFileAccess: DataFileAccess

    // @injections@
): ListValueFieldFetcher<Word> {
    /**
     * Fetches the content of the words field.
     */
    override fun fetch(environment: DataFetchingEnvironment): Mono<List<Word>> {
        val args = WordsFieldArguments.from(environment.arguments)
        // @fetch_execution@
        return mono {
            dataFileAccess.wordsData
        }

        // @fetch_execution@
        .map { result ->
            Word.from(result)
        }
        .map { results ->
            results.filter { result ->
                args.lemmas.contains(result.lemma)
            }
        }
    }
}
