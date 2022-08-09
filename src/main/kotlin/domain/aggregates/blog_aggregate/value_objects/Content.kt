package domain.aggregates.blog_aggregate.value_objects

class Content {
    private val contentElements = mutableSetOf<ContentElement>()
    fun addContentElements(vararg contentElements: ContentElement) = this.contentElements.addAll(contentElements)

    override fun hashCode(): Int {
        return contentElements.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Content

        if (contentElements != other.contentElements) return false

        return true
    }
}