package domain.factories

import com.beust.klaxon.Klaxon
import domain.aggregates.blog_aggregate.value_objects.Content
import domain.aggregates.blog_aggregate.value_objects.ContentElement
import domain.aggregates.blog_aggregate.value_objects.TextElement
import domain.aggregates.blog_aggregate.value_objects.Title
import domain.aggregates.tag_aggregate.value_objects.Name
import domain.factories.builders.TagBuilder

import utils.*

class AddBlogFactory(
    private val klaxon: Klaxon
) {
    fun anAddBlogCommand(string: String): AddBlogCommand {
        return requireNotNull(klaxon.parse<AddBlogCommand>(string))
    }
}

data class TagField(val name: String)

data class ContentElementField(
    var type: String,
    val text: String? = null,
    val style: String? = null,
    val path: String? = null,
    val caption: String? = null
)

data class AddBlogCommand(
    val title: String,
    val content: Collection<ContentElementField>,
    val tags: Collection<TagField>
) {
    fun tagNames(): List<Name> = tags.map { Name(it.name) }
    fun title(): Title = Title(title)

    // Using visitor pattern, we can get rid of the branching to create either TextElement or ImageElement
    // The solution would be to introduce another field specifies the type of content element in payload.
    // That way, through reflection we can create the class instance automatically.
    // The other benefit is that whenever in future two types happen to share the exact same format, it obviates
    // the problem of writing awkward rules to specify what to create.
    fun content(): Content =
        Content().apply { addContentElements(contentElements()) }


    private fun contentElements(): Collection<ContentElement> =
        content.map {
            it.type += "Element"
            val fields = fieldsOf(it).filterNot { field -> field == "type" }
            val fieldsToValues = fields.associateWith { field -> getValue(it, field) }.filterNotNullValues()
            val valueObjects = fieldsToValues.map { (field, value) -> createInstance(getClassPath(field), value) }

            val contentElement = createInstance(
                getClassPath(it.type),
                *valueObjects.toTypedArray()
            ) as ContentElement
            contentElement
        }

    private fun getClassPath(type: String) =
        "domain.aggregates.blog_aggregate.value_objects.${type.replaceFirstChar { firstChar -> firstChar.uppercase() }}"
}

// We're going to encapsulate all the functionality into data class.


class AddBlogResponse(val error: String? = null)