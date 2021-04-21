package laplacian.sample.service.word_list_api.graphql.type_resolver.data_file_sample.word_list

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Qualifier
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.TypeRuntimeWiring
import graphql.schema.DataFetchingEnvironment
import org.dataloader.DataLoader
import org.dataloader.DataLoaderRegistry

import reactor.core.publisher.Mono
import java.util.concurrent.CompletableFuture
import laplacian.sample.service.word_list_api.graphql.type_resolver.ListValueFieldFetcher
import laplacian.sample.service.word_list_api.graphql.type_resolver.FieldFetcher

import laplacian.sample.service.word_list_api.graphql.type_resolver.data_file_sample.word_list.*

import laplacian.sample.service.word_list_api.graphql.type_resolver.data_file_sample.word.*



/**
 * The graphql type resolver for word_list type.
 */
@Component("data_file_sample.word_list.resolver")
class WordListResolver(
    @Qualifier("data_file_sample.word_list.word_lists.fetcher")
    val wordListsFieldFetcher: ListValueFieldFetcher<WordList>,
    @Qualifier("data_file_sample.word_list.content.fetcher")
    val contentFieldFetcher: FieldFetcher<Word>,
) {
    /**
     * Fetches the value of word_lists.
     */
    fun wordLists(environment: DataFetchingEnvironment): CompletableFuture<List<WordList>>
        = wordListsFieldFetcher
            .fetch(environment)
            .toFuture()
    /**
     * Fetches the value of content.
     */
    fun content(environment: DataFetchingEnvironment): CompletableFuture<Word>
        = contentFieldFetcher
            .fetch(environment)
            .toFuture()

    /**
     * Registers the field fetchers of the fields of word_list type.
     */
    fun registerFetcher(wiring: RuntimeWiring.Builder): RuntimeWiring.Builder = wiring.type(
            TypeRuntimeWiring.newTypeWiring("DataFileSampleWordList")
        )
        .type (
            TypeRuntimeWiring.newTypeWiring("DataFileSampleWordListEntry")
            .dataFetcher("content") { env -> content(env) }
        )

    /**
     * Register dataloader used in batch request.
     */
    fun registerLoader(registry: DataLoaderRegistry): DataLoaderRegistry {
        return registry
    }
}
