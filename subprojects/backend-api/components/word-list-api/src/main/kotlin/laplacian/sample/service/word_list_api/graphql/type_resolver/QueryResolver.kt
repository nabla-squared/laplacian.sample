package laplacian.sample.service.word_list_api.graphql.type_resolver
import org.springframework.stereotype.Component
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.TypeRuntimeWiring
import graphql.schema.DataFetchingEnvironment
import org.dataloader.DataLoaderRegistry

import laplacian.sample.service.word_list_api.graphql.type_resolver.mybatis_sample.*
import laplacian.sample.service.word_list_api.graphql.type_resolver.data_file_sample.*
import laplacian.sample.service.word_list_api.graphql.type_resolver.rest_api_sample.*
import laplacian.sample.service.word_list_api.graphql.type_resolver.multi_db_sample.*


/**
 * The resolver of the graphql root query type.
 */
@Component
class QueryResolver(
    val mybatisSampleQueryResolver: MybatisSampleQueryResolver,
    val dataFileSampleQueryResolver: DataFileSampleQueryResolver,
    val restApiSampleQueryResolver: RestApiSampleQueryResolver,
    val multiDbSampleQueryResolver: MultiDbSampleQueryResolver,
) {
    /**
     * Registers the field fetchers.
     */
    fun registerFetcher(wiring: RuntimeWiring.Builder): RuntimeWiring.Builder = wiring.type(
        TypeRuntimeWiring.newTypeWiring("Query")
        .dataFetcher("mybatisSample") { _ -> emptyMap<String, String>()}
        .dataFetcher("dataFileSample") { _ -> emptyMap<String, String>()}
        .dataFetcher("restApiSample") { _ -> emptyMap<String, String>()}
        .dataFetcher("multiDbSample") { _ -> emptyMap<String, String>()}
    ).also {
        mybatisSampleQueryResolver.registerFetcher(it)
        dataFileSampleQueryResolver.registerFetcher(it)
        restApiSampleQueryResolver.registerFetcher(it)
        multiDbSampleQueryResolver.registerFetcher(it)
    }

    fun registerLoader(registry: DataLoaderRegistry): DataLoaderRegistry = registry.also {
        mybatisSampleQueryResolver.registerLoader(it)
        dataFileSampleQueryResolver.registerLoader(it)
        restApiSampleQueryResolver.registerLoader(it)
        multiDbSampleQueryResolver.registerLoader(it)
    }
}
