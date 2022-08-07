package domain.factories

import com.beust.klaxon.Klaxon
import domain.aggregates.blog_aggregate.value_objects.Content
import domain.aggregates.blog_aggregate.value_objects.ContentElement
import domain.aggregates.blog_aggregate.value_objects.TextElement
import domain.aggregates.blog_aggregate.value_objects.Title
import domain.aggregates.tag_aggregate.value_objects.Name
import domain.factories.builders.TagBuilder
import utils.createInstance
import utils.getValue
import utils.nonNullFieldsOf
import utils.setValue

class AddBlogFactory(
    private val klaxon: Klaxon
) {
    fun anAddBlogCommand(string: String): AddBlogCommand {
        return requireNotNull(klaxon.parse<AddBlogCommand>(string))
    }
}

data class TagField(val name: String)

data class ContentElementField(
    val type: String,
    val text: String? = null,
    val style: String? = null,
    val imagePath: String? = null,
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
            val contentElement =
                createInstance("domain.aggregates.blog_aggregate.value_objects.${it.type.replaceFirstChar { firstChar -> firstChar.uppercase() }}") as ContentElement
            val nonNullFields = nonNullFieldsOf(contentElement)
            nonNullFields.forEach { nonNullField ->
                setValue(contentElement, nonNullField, getValue(it, nonNullField))
            }
            contentElement
        }
}

// We're going to encapsulate all the functionality into data class.


class AddBlogResponse(val error: String? = null)