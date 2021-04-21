package laplacian.sample.service.word_list_api.data_file

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Component

/**
 * A utility class accessing data files.
 */
@Component
class DataFileAccess(
    @Value("\${graphql.data-file.word-lists.path:./data/word-lists.json}")
    private val wordListsPath: String,
    @Value("\${graphql.data-file.words.path:./data/words.json}")
    private val wordsPath: String,
) {
    val wordListsData: List<Map<String, Any?>> by lazy {
        mapper.readValue(
            FileSystemResource(wordListsPath).inputStream,
            object: TypeReference<List<Map<String, Any?>>>() {}
        )
    }
    val wordsData: List<Map<String, Any?>> by lazy {
        mapper.readValue(
            FileSystemResource(wordsPath).inputStream,
            object: TypeReference<List<Map<String, Any?>>>() {}
        )
    }

    companion object {
        val mapper = jacksonObjectMapper()
    }
}

