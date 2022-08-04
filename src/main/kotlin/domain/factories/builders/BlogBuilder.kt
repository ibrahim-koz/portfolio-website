package domain.factories.builders

import domain.aggregates.blog_aggregate.entities.Blog
import domain.aggregates.blog_aggregate.value_objects.Content
import domain.aggregates.blog_aggregate.value_objects.Popularity
import domain.aggregates.blog_aggregate.value_objects.Title
import model.Id
import utils.IdGenerator
import utils.TimeUtilityService

class BlogBuilder(private val idGenerator: IdGenerator, private val timeUtilityService: TimeUtilityService) {
    private lateinit var title: Title
    private lateinit var content: Content
    private val tags = mutableListOf<Id>()

    fun withTitle(title: Title): BlogBuilder {
        this.title = title
        return this
    }

    fun withContent(contentBuilder: ContentBuilder): BlogBuilder {
        this.content = contentBuilder.created()
        return this
    }

    fun havingTags(tagId: Id): BlogBuilder {
        this.tags.add(tagId)
        return this
    }

    fun havingTags(tagId: Collection<Id>): BlogBuilder {
        this.tags.addAll(tagId)
        return this
    }

    fun created(): Blog {
        return Blog(idGenerator.generate(), title, content, tags, Popularity(), timeUtilityService.currentTime())
    }
}