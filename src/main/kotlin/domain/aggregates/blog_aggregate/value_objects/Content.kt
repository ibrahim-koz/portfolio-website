package domain.aggregates.blog_aggregate.value_objects

class Content {
    private val contentElements = mutableSetOf<ContentElement>()
    fun addContentElement(contentElement: ContentElement) = contentElements.add(contentElement)
}