package laplacian.sample.service.word_list_api.graphql.type_resolver

import graphql.schema.DataFetchingEnvironment
import reactor.core.publisher.Mono

interface ListValueFieldBatchFetcher<K, T> {
    fun load(keys: List<K>): Mono<List<List<T>>>
}
