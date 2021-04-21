package laplacian.sample


import laplacian.util.*

/**
 * An entity describing a word.
 */
interface Word {
    /**
     * The lemma of this word.
     */
    val lemma: String
    /**
     * The rank of this word.
     */
    val rank: Int
    /**
     * The frequency of this word.
     */
    val frequency: Int
    /**
     * The japanese of this word.
     */
    val japanese: String
    /**
     * The commentary of this word.
     */
    val commentary: String
    /**
     * Returns wether this instance is a word or not.
     */
    val isaWord: Boolean
}
