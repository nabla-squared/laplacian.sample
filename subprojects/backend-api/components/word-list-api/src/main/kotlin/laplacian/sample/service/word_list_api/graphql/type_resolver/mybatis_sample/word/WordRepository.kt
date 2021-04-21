package laplacian.sample.service.word_list_api.graphql.type_resolver.mybatis_sample.word
import org.apache.ibatis.session.SqlSession
import org.springframework.stereotype.Component
import laplacian.sample.service.word_list_api.graphql.type_resolver.mybatis_sample.word.words.*


/**
 * Datasource repository for the word type resolver.
 */
@Component("mybatis_sample.word.repository")
class WordRepository (
    val wordListMainDbSqlSession: SqlSession,
) {
    /**
     * Fetches words value from the datasource.
     */
    fun words(arguments: WordsFieldArguments): List<Map<String, Any?>> =
        wordListMainDbSqlSession.selectList("mybatis_sample.word.words", mapOf(
            "args" to arguments,
        ))
}
