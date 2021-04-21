package laplacian.sample.service.word_list_api.graphql.type_resolver.rest_api_sample.word_list.word_lists

import org.springframework.stereotype.Component
import graphql.schema.DataFetchingEnvironment
import reactor.core.publisher.Mono

import laplacian.sample.service.word_list_api.graphql.type_resolver.rest_api_sample.word_list.*
import laplacian.sample.service.word_list_api.util.*

import laplacian.sample.service.word_list_api.graphql.type_resolver.ListValueFieldFetcher
// @import_statements@
import laplacian.sample.service.word_list_api.rpc.rest.google_spreadsheet.GoogleSpreadsheetRestResource
import laplacian.sample.service.word_list_api.rpc.rest.google_spreadsheet.get_values.*

// @import_statements@
/**
 * The fetcher of the word_lists of the word_list.
 */
@Component("rest_api_sample.word_list.word_lists.fetcher")
class WordListsFieldFetcher(
    // @injections@
val googleSpreadsheetRestResource: GoogleSpreadsheetRestResource,

    // @injections@
): ListValueFieldFetcher<WordList> {
    /**
     * Fetches the content of the word_lists field.
     */
    override fun fetch(environment: DataFetchingEnvironment): Mono<List<WordList>> {
        val args = WordListsFieldArguments.from(environment.arguments)
        // @fetch_execution@

        return googleSpreadsheetRestResource
        .getValues(environment.getContext())

        // @fetch_execution@
        .map { result ->
            val records= result.getAs<List<List<String>>>("values") ?: emptyList()
            WordList.from(records.map { record ->
                mapOf("name" to record[0], "content" to emptyList<Any>())
            })
        }
    }
}
