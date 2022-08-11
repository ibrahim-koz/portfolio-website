package domain.factories

import com.beust.klaxon.Klaxon
import com.sun.jdi.request.InvalidRequestStateException
import domain.aggregates.blog_aggregate.value_objects.*
import domain.aggregates.tag_aggregate.value_objects.Name

class AddBlogFactory(
    private val klaxon: Klaxon
) {
    fun anAddBlogCommand(string: String): AddBlogCommand =
        requireNotNull(klaxon.parse<AddBlogCommand>(string))
}

data class TagField(val name: String)

data class ContentElementField(
    val type: String,
) {
    lateinit var text: String
    lateinit var style: String
    lateinit var path: String
    lateinit var caption: String
}

data class AddBlogCommand(
    val title: String,
    val content: Collection<ContentElementField>,
    val tags: Collection<TagField>
) {
    fun titleAsValueObject(): Title = Title(title)

    fun contentAsValueObject(): Content =
        Content().apply { addContentElements(*contentElements()) }

    fun tagNamesAsValueObject(): List<Name> = tags.map { Name(it.name) }


    private fun contentElements() =
        content.map {
            when (it.type) {
                "text" -> TextElement(Text(it.text), Style(it.style))
                "image" -> ImageElement(Path(it.path), Caption(it.caption))
                else -> throw InvalidRequestStateException()
            }
        }.toTypedArray()
}

class AddBlogResponse(val error: String? = null)