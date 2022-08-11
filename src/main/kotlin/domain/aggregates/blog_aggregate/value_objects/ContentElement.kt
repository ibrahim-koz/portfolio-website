package domain.aggregates.blog_aggregate.value_objects

abstract class ContentElement

data class TextElement(val text: Text, val style: Style) : ContentElement()

data class ImageElement(val path: Path, val caption: Caption) : ContentElement()

data class CodeSnippetElement(val text: Text, val language: Language) : ContentElement()