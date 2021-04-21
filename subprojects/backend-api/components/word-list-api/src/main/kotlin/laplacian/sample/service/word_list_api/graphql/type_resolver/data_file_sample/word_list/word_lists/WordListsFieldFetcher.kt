package laplacian.sample.service.word_list_api.graphql.type_resolver.data_file_sample.word_list.word_lists

import org.springframework.stereotype.Component
import graphql.schema.DataFetchingEnvironment
import reactor.core.publisher.Mono

import laplacian.sample.service.word_list_api.graphql.type_resolver.data_file_sample.word_list.*
import laplacian.sample.service.word_list_api.util.*

import laplacian.sample.service.word_list_api.graphql.type_resolver.ListValueFieldFetcher
// @import_statements@
import kotlinx.coroutines.reactor.mono
import laplacian.sample.service.word_list_api.data_file.DataFileAccess
// @import_statements@
/**
 * The fetcher of the word_lists of the word_list.
 */
@Component("data_file_sample.word_list.word_lists.fetcher")
class WordListsFieldFetcher(
    // @injections@
    val dataFileAccess: DataFileAccess

    // @injections@
): ListValueFieldFetcher<WordList> {
    /**
     * Fetches the content of the word_lists field.
     */
    override fun fetch(environment: DataFetchingEnvironment): Mono<List<WordList>> {
        // @fetch_execution@
        return mono {
            dataFileAccess.wordListsData
        }

        // @fetch_execution@
        .map { result ->
            WordList.from(result)
        }
    }
}
