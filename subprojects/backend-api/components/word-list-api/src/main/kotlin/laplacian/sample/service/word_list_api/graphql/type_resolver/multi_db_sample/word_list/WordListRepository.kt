package laplacian.sample.service.word_list_api.graphql.type_resolver.multi_db_sample.word_list
import org.apache.ibatis.session.SqlSession
import org.springframework.stereotype.Component
import laplacian.sample.service.word_list_api.graphql.type_resolver.multi_db_sample.word_list.word_lists.*

import laplacian.sample.service.word_list_api.graphql.type_resolver.multi_db_sample.word_list.content.*
import laplacian.sample.service.word_list_api.graphql.type_resolver.multi_db_sample.word.*


/**
 * Datasource repository for the word_list type resolver.
 */
@Component("multi_db_sample.word_list.repository")
class WordListRepository (
    val wordListMainDbSqlSession: SqlSession,
    val wordListSubDbSqlSession: SqlSession,
) {
    /**
     * Fetches word_lists value from the datasource.
     */
    fun wordLists(arguments: WordListsFieldArguments): List<Map<String, Any?>> =
        wordListMainDbSqlSession.selectList("multi_db_sample.word_list.wordLists", mapOf(
            "args" to arguments,
        ))
    /**
     * Fetches content value from the datasource.
     */
    fun content(self: WordListEntry, ): Map<String, Any?> =
        wordListSubDbSqlSession.selectOne("multi_db_sample.word_list.content", mapOf(
            "self" to self,
        ))
}
