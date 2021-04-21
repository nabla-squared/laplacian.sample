package laplacian.sample.service.word_list_api.graphql.type_resolver.rest_api_sample.word.words

import org.springframework.stereotype.Component
import graphql.schema.DataFetchingEnvironment
import reactor.core.publisher.Mono

import laplacian.sample.service.word_list_api.graphql.type_resolver.rest_api_sample.word.*
import laplacian.sample.service.word_list_api.util.*

import laplacian.sample.service.word_list_api.graphql.type_resolver.ListValueFieldFetcher
// @import_statements@
import laplacian.sample.service.word_list_api.rpc.rest.google_spreadsheet.GoogleSpreadsheetRestResource
import laplacian.sample.service.word_list_api.rpc.rest.google_spreadsheet.get_values.*

// @import_statements@
/**
 * The fetcher of the words of the word.
 */
@Component("rest_api_sample.word.words.fetcher")
class WordsFieldFetcher(
    // @injections@
val googleSpreadsheetRestResource: GoogleSpreadsheetRestResource,

    // @injections@
    val conf: WordResolverConfig,
): ListValueFieldFetcher<Word> {
    /**
     * Fetches the content of the words field.
     */
    override fun fetch(environment: DataFetchingEnvironment): Mono<List<Word>> {
        val args = WordsFieldArguments.from(environment.arguments)
        // @fetch_execution@

        return googleSpreadsheetRestResource
        .getValues(GetValuesInput(
            range = "A:E",
            spreadsheetId = conf.spreadsheetId,
        ))

        // @fetch_execution@
        .map { result ->
            val records= result.getAs<List<List<String>>>("values") ?: emptyList()
            Word.from(records.drop(1).map { record ->
                mapOf(
                    "lemma" to record[0],
                    "rank" to record[1]?.toInt(),
                    "frequency" to record[2]?.toInt(),
                    "japanese" to record[3],
                    "commentary" to record[4],
                )
            })
        }
    }
}
