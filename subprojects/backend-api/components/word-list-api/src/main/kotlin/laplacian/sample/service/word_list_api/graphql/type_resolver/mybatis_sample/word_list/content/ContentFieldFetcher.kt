package laplacian.sample.service.word_list_api.graphql.type_resolver.mybatis_sample.word_list.content

import org.springframework.stereotype.Component
import graphql.schema.DataFetchingEnvironment
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import laplacian.sample.service.word_list_api.graphql.type_resolver.mybatis_sample.word_list.*
import laplacian.sample.service.word_list_api.util.*
import laplacian.sample.service.word_list_api.graphql.type_resolver.mybatis_sample.word.*
import laplacian.sample.service.word_list_api.graphql.type_resolver.FieldBatchFetcher
// @import_statements@
import kotlinx.coroutines.reactor.mono
// @import_statements@
/**
 * The fetcher of the content of the word_list.
 */
@Component("mybatis_sample.word_list.content.fetcher")
class ContentFieldFetcher(
    // @injections@
    val repository: WordListRepository,

    // @injections@
): FieldBatchFetcher<WordListEntry, Word> {
    /**
     * Load the content of the content field in batch.
     */
    override fun load(keys: List<WordListEntry>): Mono<List<Word>> {
        // @fetch_execution@


        return mono {
            repository.content(keys)
        }

        // @fetch_execution@
        .map { result ->
            Word.from(result)
        }
    }
}
