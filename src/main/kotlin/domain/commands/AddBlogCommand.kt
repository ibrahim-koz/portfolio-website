package domain.commands

import com.sun.jdi.request.InvalidRequestStateException
import domain.aggregates.blog_aggregate.value_objects.*
import domain.aggregates.tag_aggregate.value_objects.Name

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
                "codeSnippet" -> CodeSnippetElement(Text(it.body), Language(it.language))
                else -> throw InvalidRequestStateException()
            }
        }.toTypedArray()
}