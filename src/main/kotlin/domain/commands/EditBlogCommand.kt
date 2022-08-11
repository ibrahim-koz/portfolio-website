package domain.commands

import com.sun.jdi.request.InvalidRequestStateException
import domain.aggregates.blog_aggregate.value_objects.*
import domain.aggregates.tag_aggregate.value_objects.Name
import model.Id


// TODO: we must cache the result of the function calls.
data class EditBlogCommand(
    val id: Int,
    val title: String?,
    val content: Collection<ContentElementField>?,
    val tags: Collection<TagField>?
) {
    fun idAsValueObject(): Id = Id(id)

    fun titleAsValueObject(): Title? = title?.let { Title(it) }

    fun contentAsValueObject(): Content? {
        val contentElements = contentElements()
        contentElements?.let {
            return Content().apply { addContentElements(*it) }
        }

        return null
    }

    fun tagNamesAsValueObject(): List<Name>? = tags?.map { Name(it.name) }


    private fun contentElements() =
        content?.map {
            when (it.type) {
                "text" -> TextElement(Text(it.text), Style(it.style))
                "image" -> ImageElement(Path(it.path), Caption(it.caption))
                "codeSnippet" -> CodeSnippetElement(Text(it.body), Language(it.language))
                else -> throw InvalidRequestStateException()
            }
        }?.toTypedArray()
}
