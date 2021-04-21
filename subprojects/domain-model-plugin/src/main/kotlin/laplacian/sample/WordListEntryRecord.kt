package laplacian.sample
import com.github.jknack.handlebars.Context
import laplacian.sample.WordList

import laplacian.sample.Word




import laplacian.generate.util.*
/**
 * An entity describing a word_list_entry.
 */
data class WordListEntryRecord (
    private val __record: Record,
    private val _context: Context,
    override val wordList: WordList,
    private val _record: Record = __record.normalizeCamelcase()
): WordListEntry, Record by _record {

    /**
     * The lemma of this word_list_entry.
     */
    override val lemma: String
        get() = getOrThrow("lemma")
    /**
     * The word of this word_list_entry.
     */
    override val word: Word by lazy {
        WordRecord.from(_context).find {
            it.lemma == lemma
        } ?: throw IllegalStateException(
            "There is no word which meets the following condition(s): "
            + "WordListEntry.lemma == word.lemma (=$lemma) "
            + "Possible values are: " + WordRecord.from(_context).map {
              "(${ it.lemma })"
            }.joinToString()
        )
    }
    /**
     * Returns wether this instance is a word_list_entry or not.
     */
    override val isaWordListEntry: Boolean = true

    companion object {
        /**
         * Creates record list from list of map.
         */
        fun from(records: RecordList, _context: Context, wordList: WordList) = records
            .mergeWithKeys("lemma")
            .map { from(it, _context, wordList = wordList) }

        fun from(record: Record, _context: Context, wordList: WordList) =
            WordListEntryRecord(record, _context, wordList = wordList)
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is WordListEntryRecord) return false
        if (wordList != other.wordList) return false
        if (lemma != other.lemma) return false
        return true
    }

    override fun hashCode(): Int {
        var result = wordList.hashCode()
        result = 31 * result + lemma.hashCode()
        return result
    }

    override fun toString(): String {
        return "WordListEntryRecord(" +
            "wordList=$wordList, " +
            "lemma=$lemma" +
        ")"
    }
}
