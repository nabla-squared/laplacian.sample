package laplacian.sample.service.word_list_api.rpc.rest.google_spreadsheet

import org.springframework.stereotype.Component
import org.springframework.http.HttpMethod

import reactor.core.publisher.Mono
import org.slf4j.*

import laplacian.sample.service.word_list_api.rpc.rest.RestResourceClientBase
import laplacian.sample.service.word_list_api.rpc.rest.google_spreadsheet.get_values.*


/**
 * An implementation of the GoogleSpreadsheetRestResource interface
 */
@Component
class GoogleSpreadsheetRestResourceClient(
    private val conf: GoogleSpreadsheetRestResourceConfig,
): RestResourceClientBase(), GoogleSpreadsheetRestResource {
    /**
     * GET /{spreadsheet_id}/values/{range}
     */
    override fun getValues(args: GetValuesInput): Mono<Map<String, Any?>> =
        client("https://content-sheets.googleapis.com/v4/spreadsheets")
        .method(HttpMethod.GET)
        .uri { uri -> uri
            .path("/{spreadsheet_id}/values/{range}")
            .let { queryParam(it, "key", conf.apiKey) }
            .build(mapOf(
                "spreadsheet_id" to args.spreadsheetId,
                "range" to args.range,
            ))
        }
        .let { commonRequestSettings(it) }
        .exchange()
        .flatMap { response ->
            response.bodyToMono(Map::class.java) as Mono<Map<String, Any?>>
        }

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(GoogleSpreadsheetRestResourceClient::class.java)
    }
}