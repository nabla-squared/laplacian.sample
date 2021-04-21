package laplacian.sample
import com.github.jknack.handlebars.Context

import laplacian.util.*

/**
 * All words.
 */
class AllWords(
    list: List<Word>,
    val context: Context
) : List<Word> by list {
}
