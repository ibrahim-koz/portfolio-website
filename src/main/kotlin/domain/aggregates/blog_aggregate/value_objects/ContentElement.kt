package domain.aggregates.blog_aggregate.value_objects

abstract class ContentElement

data class TextElement(val text: Text, val style: Style) : ContentElement()

data class ImageElement(val picturePath: Path, val caption: Text) : ContentElement()