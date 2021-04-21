package laplacian.sample.service.word_list_api.graphql.type_resolver.rest_api_sample.word

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component("rest_api_sample.word.resolver.config")
data class WordResolverConfig (
    @Value("\${rest-api-sample-word.spreadsheet-id}")
    val spreadsheetId: String,
)
