package laplacian.sample.service.word_list_api.rpc.rest.google_spreadsheet

import graphql.GraphQLContext
import reactor.core.publisher.Mono
import laplacian.sample.service.word_list_api.rpc.rest.google_spreadsheet.get_values.*


/**
 * The REST interface of the google_spreadsheet resource.
 */
interface GoogleSpreadsheetRestResource {
    fun getValues(args: GetValuesInput): Mono<Map<String, Any?>>
}