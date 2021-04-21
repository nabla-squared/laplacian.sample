package laplacian.sample.service.word_list_api.graphql.type_resolver.rest_api_sample
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Qualifier
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.TypeRuntimeWiring
import graphql.schema.DataFetchingEnvironment
import org.dataloader.DataLoader
import org.dataloader.DataLoaderRegistry
import reactor.core.publisher.Mono
import java.util.concurrent.CompletableFuture

import laplacian.sample.service.word_list_api.graphql.type_resolver.rest_api_sample.word_list.*


import laplacian.sample.service.word_list_api.graphql.type_resolver.rest_api_sample.word.*



/**
 * The root query resolver for the rest_api_sample type group.
 */
@Component
class RestApiSampleQueryResolver(
    @Qualifier("rest_api_sample.word_list.resolver")
    val wordListResolver: WordListResolver,
    @Qualifier("rest_api_sample.word.resolver")
    val wordResolver: WordResolver,
) {
    /**
     * The word_lists field.
     */
    fun wordLists(context: DataFetchingEnvironment): CompletableFuture<List<WordList>>
        = wordListResolver.wordLists(context)
    /**
     * The words field.
     */
    fun words(context: DataFetchingEnvironment): CompletableFuture<List<Word>>
        = wordResolver.words(context)

    /**
     * Registers all the field fetchers of graphql types which belong to this group.
     */
    fun registerFetcher(wiring: RuntimeWiring.Builder): RuntimeWiring.Builder = wiring.type(
        TypeRuntimeWiring.newTypeWiring("RestApiSample")
        .dataFetcher("wordLists") { env -> wordLists(env) }
        .dataFetcher("words") { env -> words(env) }
    ).also {
        wordListResolver.registerFetcher(it)
        wordResolver.registerFetcher(it)
    }
    fun registerLoader(registry: DataLoaderRegistry): DataLoaderRegistry = registry.also {
        wordListResolver.registerLoader(it)
        wordResolver.registerLoader(it)
    }
}
