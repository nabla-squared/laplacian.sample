package laplacian.sample.service.word_list_api.graphql.type_resolver.multi_db_sample.word_list.word_lists

import org.springframework.stereotype.Component
import graphql.schema.DataFetchingEnvironment
import reactor.core.publisher.Mono

import laplacian.sample.service.word_list_api.graphql.type_resolver.multi_db_sample.word_list.*
import laplacian.sample.service.word_list_api.util.*

import laplacian.sample.service.word_list_api.graphql.type_resolver.ListValueFieldFetcher
// @import_statements@
import kotlinx.coroutines.reactor.mono
// @import_statements@
/**
 * The fetcher of the word_lists of the word_list.
 */
@Component("multi_db_sample.word_list.word_lists.fetcher")
class WordListsFieldFetcher(
    // @injections@
    val repository: WordListRepository,

    // @injections@
): ListValueFieldFetcher<WordList> {
    /**
     * Fetches the content of the word_lists field.
     */
    override fun fetch(environment: DataFetchingEnvironment): Mono<List<WordList>> {
        val args = WordListsFieldArguments.from(environment.arguments)
        // @fetch_execution@


        return mono {
            repository.wordLists(args)
        }
        .map { records ->
            records
            .groupBy { mapOf(
                "name" to it["name"],
            )}
            .map { (k, v) -> k + mapOf(
                "content" to v.fold(mutableListOf<Map<String, Any?>>()) { acc, map -> acc.apply {
                    if (map.containsKey("content")) add(map["content"] as Map<String, Any?>)
                }}
            )}
        }

        // @fetch_execution@
        .map { result ->
            WordList.from(result)
        }
    }
}
