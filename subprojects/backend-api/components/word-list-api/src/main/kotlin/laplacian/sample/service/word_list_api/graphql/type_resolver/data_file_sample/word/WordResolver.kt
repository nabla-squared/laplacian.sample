package laplacian.sample.service.word_list_api.graphql.type_resolver.data_file_sample.word

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

import laplacian.sample.service.word_list_api.graphql.type_resolver.data_file_sample.word.*



/**
 * The graphql type resolver for word type.
 */
@Component("data_file_sample.word.resolver")
class WordResolver(
    @Qualifier("data_file_sample.word.words.fetcher")
    val wordsFieldFetcher: ListValueFieldFetcher<Word>,
) {
    /**
     * Fetches the value of words.
     */
    fun words(environment: DataFetchingEnvironment): CompletableFuture<List<Word>>
        = wordsFieldFetcher
            .fetch(environment)
            .toFuture()

    /**
     * Registers the field fetchers of the fields of word type.
     */
    fun registerFetcher(wiring: RuntimeWiring.Builder): RuntimeWiring.Builder = wiring.type(
            TypeRuntimeWiring.newTypeWiring("DataFileSampleWord")
        )

    /**
     * Register dataloader used in batch request.
     */
    fun registerLoader(registry: DataLoaderRegistry): DataLoaderRegistry {
        return registry
    }
}
