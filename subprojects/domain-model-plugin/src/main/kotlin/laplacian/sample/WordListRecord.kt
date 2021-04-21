package laplacian.sample
import com.github.jknack.handlebars.Context
import laplacian.sample.WordListEntry




import laplacian.generate.util.*
/**
 * An entity describing a word_list.
 */
data class WordListRecord (
    private val __record: Record,
    private val _context: Context,
    private val _record: Record = __record.normalizeCamelcase()
): WordList, Record by _record {

    /**
     * The name of this word_list.
     */
    override val name: String
        get() = getOrThrow("name")
    /**
     * The content of this word_list.
     */
    override val content: List<WordListEntry> by lazy {
        WordListEntryRecord.from(_record.getList("content", emptyList()), _context, this)
    }
    /**
     * Returns wether this instance is a word_list or not.
     */
    override val isaWordList: Boolean = true

    companion object {
        /**
         * Creates record list from list of map.
         */
        fun from(_context: Context): AllWordLists {
            return _context.get("word_lists") as AllWordLists
        }
        fun from(records: RecordList, _context: Context) = records.map { from(it, _context) }

        fun from(record: Record, _context: Context) =
            WordListRecord(record, _context)
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is WordListRecord) return false
        if (name != other.name) return false
        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        return result
    }

    override fun toString(): String {
        return "WordListRecord(" +
            "name=$name" +
        ")"
    }
}
