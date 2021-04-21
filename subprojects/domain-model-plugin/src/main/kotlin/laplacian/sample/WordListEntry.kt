package laplacian.sample


import laplacian.util.*

/**
 * An entity describing a word_list_entry.
 */
interface WordListEntry {
    /**
     * The lemma of this word_list_entry.
     */
    val lemma: String
    /**
     * The word_list of this word_list_entry.
     */
    val wordList: WordList
    /**
     * The word of this word_list_entry.
     */
    val word: Word
    /**
     * Returns wether this instance is a word_list_entry or not.
     */
    val isaWordListEntry: Boolean
}
