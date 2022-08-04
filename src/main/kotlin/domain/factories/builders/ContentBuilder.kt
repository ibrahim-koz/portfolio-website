package domain.factories.builders

import domain.aggregates.blog_aggregate.value_objects.Content
import domain.aggregates.blog_aggregate.value_objects.ContentElement


class ContentBuilder {
    private val contentElements = mutableListOf<ContentElement>()
    fun havingContentElements(contentElement: ContentElement): ContentBuilder {
        contentElements.add(contentElement)
        return this
    }

    fun havingContentElements(contentElement: Collection<ContentElement>): ContentBuilder {
        contentElements.addAll(contentElement)
        return this
    }

    fun created(): Content {
        return Content(contentElements)
    }
}