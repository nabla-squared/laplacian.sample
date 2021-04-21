package laplacian.sample.plugin
import org.pf4j.Extension
import laplacian.generate.ModelEntryResolver
import laplacian.generate.ExecutionContext
import laplacian.sample.AllWords
import laplacian.sample.WordRecord
import laplacian.sample.AllWordLists
import laplacian.sample.WordListRecord

import laplacian.generate.util.*

@Extension
class DomainModelModelEntryResolver: ModelEntryResolver {

    override fun resolves(key: String, model: Map<String, Any?>): Boolean {
        return arrayOf(
            "words",
            "word_lists"
        ).any { it == key }
    }

    override fun resolve(key: String, model: Map<String, Any?>, context: ExecutionContext): Any? {
        return when (key) {
            "words" -> AllWords(
                model.getList<Record>("words", emptyList())
                     .mergeWithKeys("lemma")
                     .let{ WordRecord.from(it, context.currentModel) },
                context.currentModel
            )
            "word_lists" -> AllWordLists(
                model.getList<Record>("word_lists", emptyList())
                     .mergeWithKeys("name")
                     .let{ WordListRecord.from(it, context.currentModel) },
                context.currentModel
            )
            else -> throw IllegalArgumentException("Unknown key: $key")
        }
    }
}
