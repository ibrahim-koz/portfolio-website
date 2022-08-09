package domain.factories

import com.beust.klaxon.Klaxon
import com.sun.jdi.request.InvalidRequestStateException
import domain.aggregates.blog_aggregate.value_objects.*
import domain.aggregates.tag_aggregate.value_objects.Name
import domain.factories.builders.TagBuilder

import utils.*
import kotlin.reflect.jvm.internal.impl.types.TypeCheckerState.SupertypesPolicy.None

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
            when (it.type) {
                "text" -> TextElement(Text(it.text), Style(it.style))
                "image" -> ImageElement(Path(it.path), Caption(it.caption))
                else -> throw InvalidRequestStateException()
            }
        }
}

// We're going to encapsulate all the functionality into data class.


class AddBlogResponse(val error: String? = null)