package laplacian.sample


import laplacian.util.*

/**
 * An entity describing a word_list.
 */
interface WordList {
    /**
     * The name of this word_list.
     */
    val name: String
    /**
     * The content of this word_list.
     */
    val content: List<WordListEntry>
    /**
     * Returns wether this instance is a word_list or not.
     */
    val isaWordList: Boolean
}
