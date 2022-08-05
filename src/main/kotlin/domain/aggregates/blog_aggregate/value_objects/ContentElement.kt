package domain.aggregates.blog_aggregate.value_objects

abstract class ContentElement {

}

class TextElement(val text: Text, val style: Style): ContentElement() {

}

class ImageElement(val picturePath: Path, val caption: Text): ContentElement() {

}