package domain.aggregates.blog_aggregate.value_objects

abstract class ContentElement

data class TextElement(val style: Style, val text: Text) : ContentElement()

data class ImageElement(val caption: Caption, val path: Path) : ContentElement()