package laplacian.sample
import com.github.jknack.handlebars.Context

import laplacian.util.*

/**
 * All word_lists.
 */
class AllWordLists(
    list: List<WordList>,
    val context: Context
) : List<WordList> by list {
}
