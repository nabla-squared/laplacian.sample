package laplacian.sample.service.word_list_api.graphql.type_resolver.multi_db_sample.word
import org.apache.ibatis.session.SqlSession
import org.springframework.stereotype.Component
import laplacian.sample.service.word_list_api.graphql.type_resolver.multi_db_sample.word.words.*


/**
 * Datasource repository for the word type resolver.
 */
@Component("multi_db_sample.word.repository")
class WordRepository (
    val wordListSubDbSqlSession: SqlSession,
) {
    /**
     * Fetches words value from the datasource.
     */
    fun words(arguments: WordsFieldArguments): List<Map<String, Any?>> =
        wordListSubDbSqlSession.selectList("multi_db_sample.word.words", mapOf(
            "args" to arguments,
        ))
}
