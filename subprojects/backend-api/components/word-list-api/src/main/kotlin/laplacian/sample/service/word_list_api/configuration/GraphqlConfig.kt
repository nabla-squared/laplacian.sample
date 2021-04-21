package laplacian.sample.service.word_list_api.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.ApplicationContext
import org.springframework.util.StreamUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.annotation.Qualifier
import graphql.GraphQL
import graphql.schema.GraphQLSchema
import graphql.schema.idl.SchemaParser
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.RuntimeWiring
import graphql.schema.visibility.BlockedFields
import graphql.schema.visibility.GraphqlFieldVisibility
import graphql.execution.AsyncExecutionStrategy
import graphql.execution.DataFetcherExceptionHandler

import graphql.scalars.ExtendedScalars
import org.dataloader.DataLoaderRegistry
import org.slf4j.LoggerFactory
import java.nio.charset.StandardCharsets

import laplacian.sample.service.word_list_api.graphql.type_resolver.QueryResolver

@Configuration
class GraphqlConfig(
    @Value("\${graphql.blocked-fields-patterns:}")
    private val blockedFieldsPatterns: String,
) {
    @Bean
    fun graphql(
        schema: GraphQLSchema,
        exceptionHandler: DataFetcherExceptionHandler?,
    ): GraphQL = GraphQL
        .newGraphQL(schema)
        .apply { if (exceptionHandler != null) queryExecutionStrategy(AsyncExecutionStrategy(exceptionHandler)) }
        .build()

    @Bean
    fun blockedFields(): GraphqlFieldVisibility = blockedFieldsPatterns
        .split(",")
        .fold(BlockedFields.newBlock()) { block, pattern ->
            LOG.info("Ignoring the following graphql fields: {}", pattern)
            block.addPattern(pattern)
        }
        .build()

    @Bean
    fun buildSchema(
        context: ApplicationContext,
        runtimeWiring: RuntimeWiring
    ): GraphQLSchema {
        val parser = SchemaParser()
        val schema = context
            .getResources("classpath*:/**/*.graphql")
            .map {
                StreamUtils.copyToString(it.inputStream, StandardCharsets.UTF_8)
            }
            .joinToString("\n")
        val typeRegistry = parser.parse(schema)
        return SchemaGenerator().makeExecutableSchema(typeRegistry, runtimeWiring)
    }

    @Bean
    fun buildRuntimeWiring(
        queryResolver: QueryResolver,
        fieldVisibility: GraphqlFieldVisibility,
    ): RuntimeWiring = RuntimeWiring.newRuntimeWiring()
    .scalar(ExtendedScalars.newRegexScalar("DateTime").addPattern(
        """^[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}Z?$""".toPattern()
    ).build())
    .scalar(ExtendedScalars.newRegexScalar("Date").addPattern(
        """^[0-9]{4}-[0-9]{2}-[0-9]{2}$""".toPattern()
    ).build())
    .scalar(ExtendedScalars.Time)
    .also {
        queryResolver.registerFetcher(it)
    }
    .fieldVisibility(fieldVisibility)
    .build()

    @Bean
    fun buildDataLoaderRegistry(
        queryResolver: QueryResolver
    ): DataLoaderRegistry = DataLoaderRegistry().also {
        queryResolver.registerLoader(it)
    }

    companion object {
        val LOG = LoggerFactory.getLogger(GraphqlConfig::class.java)
    }
}