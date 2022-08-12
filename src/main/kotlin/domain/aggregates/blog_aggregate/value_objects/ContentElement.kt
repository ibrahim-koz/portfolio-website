package domain.aggregates.blog_aggregate.value_objects

abstract class ContentElement

data class TextElement(private val text: Text, private val style: Style) : ContentElement()

data class ImageElement(private val path: Path, private val caption: Caption) : ContentElement()

data class CodeSnippetElement(private val text: Text, private val language: Language) : ContentElement()