package laplacian.sample
import com.github.jknack.handlebars.Context



import laplacian.generate.util.*
/**
 * An entity describing a word.
 */
data class WordRecord (
    private val __record: Record,
    private val _context: Context,
    private val _record: Record = __record.normalizeCamelcase()
): Word, Record by _record {

    /**
     * The lemma of this word.
     */
    override val lemma: String
        get() = getOrThrow("lemma")
    /**
     * The rank of this word.
     */
    override val rank: Int
        get() = getOrThrow("rank")
    /**
     * The frequency of this word.
     */
    override val frequency: Int
        get() = getOrThrow("frequency")
    /**
     * The japanese of this word.
     */
    override val japanese: String
        get() = getOrThrow("japanese")
    /**
     * The commentary of this word.
     */
    override val commentary: String
        get() = getOrThrow("commentary")
    /**
     * Returns wether this instance is a word or not.
     */
    override val isaWord: Boolean = true

    companion object {
        /**
         * Creates record list from list of map.
         */
        fun from(_context: Context): AllWords {
            return _context.get("words") as AllWords
        }
        fun from(records: RecordList, _context: Context) = records.map { from(it, _context) }

        fun from(record: Record, _context: Context) =
            WordRecord(record, _context)
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is WordRecord) return false
        if (lemma != other.lemma) return false
        return true
    }

    override fun hashCode(): Int {
        var result = lemma.hashCode()
        return result
    }

    override fun toString(): String {
        return "WordRecord(" +
            "lemma=$lemma" +
        ")"
    }
}
