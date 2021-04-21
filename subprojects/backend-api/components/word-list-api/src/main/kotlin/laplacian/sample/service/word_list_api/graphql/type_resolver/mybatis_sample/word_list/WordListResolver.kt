package laplacian.sample.service.word_list_api.graphql.type_resolver.mybatis_sample.word_list

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Qualifier
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.TypeRuntimeWiring
import graphql.schema.DataFetchingEnvironment
import org.dataloader.DataLoader
import org.dataloader.DataLoaderRegistry
import org.dataloader.BatchLoader
import reactor.core.publisher.Mono
import java.util.concurrent.CompletableFuture
import laplacian.sample.service.word_list_api.graphql.type_resolver.ListValueFieldFetcher
import laplacian.sample.service.word_list_api.graphql.type_resolver.FieldBatchFetcher

import laplacian.sample.service.word_list_api.graphql.type_resolver.mybatis_sample.word_list.*

import laplacian.sample.service.word_list_api.graphql.type_resolver.mybatis_sample.word.*



/**
 * The graphql type resolver for word_list type.
 */
@Component("mybatis_sample.word_list.resolver")
class WordListResolver(
    @Qualifier("mybatis_sample.word_list.word_lists.fetcher")
    val wordListsFieldFetcher: ListValueFieldFetcher<WordList>,
    @Qualifier("mybatis_sample.word_list.content.fetcher")
    val contentFieldFetcher: FieldBatchFetcher<WordListEntry, Word>,
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
        = environment
            .getDataLoader<WordListEntry, Word>("mybatis_sample.word_list.content.loader")
            .load(environment.getSource() as WordListEntry)

    /**
     * Registers the field fetchers of the fields of word_list type.
     */
    fun registerFetcher(wiring: RuntimeWiring.Builder): RuntimeWiring.Builder = wiring.type(
            TypeRuntimeWiring.newTypeWiring("MybatisSampleWordList")
        )
        .type (
            TypeRuntimeWiring.newTypeWiring("MybatisSampleWordListEntry")
            .dataFetcher("content") { env -> content(env) }
        )

    /**
     * Register dataloader used in batch request.
     */
    fun registerLoader(registry: DataLoaderRegistry): DataLoaderRegistry {
        val contentLoader = BatchLoader<WordListEntry, Word> { keys ->
            contentFieldFetcher.load(keys).toFuture()
        }
        return registry
        .register(
            "mybatis_sample.word_list.content.loader",
            DataLoader.newDataLoader(contentLoader)
        )
    }
}
