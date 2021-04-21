package laplacian.sample.service.word_list_api.graphql.type_resolver.data_file_sample.word_list.content

import org.springframework.stereotype.Component
import graphql.schema.DataFetchingEnvironment
import reactor.core.publisher.Mono

import laplacian.sample.service.word_list_api.graphql.type_resolver.data_file_sample.word_list.*
import laplacian.sample.service.word_list_api.util.*
import laplacian.sample.service.word_list_api.graphql.type_resolver.data_file_sample.word.*
import laplacian.sample.service.word_list_api.graphql.type_resolver.FieldFetcher
// @import_statements@
import kotlinx.coroutines.reactor.mono
import laplacian.sample.service.word_list_api.data_file.DataFileAccess
// @import_statements@
/**
 * The fetcher of the content of the word_list.
 */
@Component("data_file_sample.word_list.content.fetcher")
class ContentFieldFetcher(
    // @injections@
    val dataFileAccess: DataFileAccess

    // @injections@
): FieldFetcher<Word> {
    /**
     * Fetches the content of the content field.
     */
    override fun fetch(environment: DataFetchingEnvironment): Mono<Word> {
        val self: WordListEntry = environment.getSource()
        // @fetch_execution@
        return mono {
            dataFileAccess.wordsData
        }

        // @fetch_execution@
        .map { result ->
            Word.from(result)
        }
        .map { results ->
            results.find { result ->
                result.lemma == self.lemma
            }
        }
    }
}
