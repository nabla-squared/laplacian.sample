package laplacian.sample.service.word_list_api.rpc.rest.google_spreadsheet.get_values

import laplacian.sample.service.word_list_api.util.*
import java.time.*

data class GetValuesInput (
    val range: String? = null,
    val spreadsheetId: String? = null,
) {
    companion object {
        fun from(args: Map<String, Any?>): GetValuesInput {
            return GetValuesInput(
                range = args["range"] as? String,
                spreadsheetId = args["spreadsheetId"] as? String,
            )
        }
    }
}
