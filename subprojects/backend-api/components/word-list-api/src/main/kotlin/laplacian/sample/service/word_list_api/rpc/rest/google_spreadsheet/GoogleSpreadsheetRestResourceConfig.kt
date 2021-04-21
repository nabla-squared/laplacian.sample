package laplacian.sample.service.word_list_api.rpc.rest.google_spreadsheet

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class GoogleSpreadsheetRestResourceConfig(
    @Value("\${google-spreadsheet.api-key}")
    val apiKey: String,
)