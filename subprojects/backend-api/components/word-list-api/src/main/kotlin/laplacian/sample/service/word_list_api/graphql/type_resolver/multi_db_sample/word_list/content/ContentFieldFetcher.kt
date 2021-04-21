package laplacian.sample.service.word_list_api.graphql.type_resolver.multi_db_sample.word_list.content

import org.springframework.stereotype.Component
import graphql.schema.DataFetchingEnvironment
import reactor.core.publisher.Mono

import laplacian.sample.service.word_list_api.graphql.type_resolver.multi_db_sample.word_list.*
import laplacian.sample.service.word_list_api.util.*
import laplacian.sample.service.word_list_api.graphql.type_resolver.multi_db_sample.word.*
import laplacian.sample.service.word_list_api.graphql.type_resolver.FieldFetcher
// @import_statements@
import kotlinx.coroutines.reactor.mono
// @import_statements@
/**
 * The fetcher of the content of the word_list.
 */
@Component("multi_db_sample.word_list.content.fetcher")
class ContentFieldFetcher(
    // @injections@
    val repository: WordListRepository,

    // @injections@
): FieldFetcher<Word> {
    /**
     * Fetches the content of the content field.
     */
    override fun fetch(environment: DataFetchingEnvironment): Mono<Word> {
        val self: WordListEntry = environment.getSource()
        // @fetch_execution@


        return mono {
            repository.content(self)
        }

        // @fetch_execution@
        .map { result ->
            Word.from(result)
        }
    }
}
