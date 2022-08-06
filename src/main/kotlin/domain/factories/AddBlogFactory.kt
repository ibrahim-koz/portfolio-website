package domain.factories

import com.beust.klaxon.Klaxon



class AddBlogFactory(
    private val klaxon: Klaxon
) {
    fun anAddBlogCommand(string: String): AddBlogCommand {
        return requireNotNull(klaxon.parse<AddBlogCommand>(string))
    }
}

data class TagField(val name: String)

data class ContentElementField(
    val text: String? = null,
    val style: String? = null,
    val imagePath: String? = null,
    val caption: String? = null
)

data class AddBlogCommand(val title: String, val content: Collection<ContentElementField>, val tags: Collection<TagField>)

class AddBlogResponse(val error: String?)