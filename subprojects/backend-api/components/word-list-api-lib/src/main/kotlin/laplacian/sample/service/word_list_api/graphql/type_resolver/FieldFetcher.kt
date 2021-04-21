package laplacian.sample.service.word_list_api.graphql.type_resolver

import graphql.schema.DataFetchingEnvironment
import reactor.core.publisher.Mono

interface FieldFetcher<T> {
    fun fetch(environment: DataFetchingEnvironment): Mono<T>
}
