package domain.aggregates.blog_aggregate.entities

import domain.aggregates.blog_aggregate.value_objects.Content
import domain.aggregates.blog_aggregate.value_objects.Popularity
import domain.aggregates.blog_aggregate.value_objects.Time
import domain.aggregates.blog_aggregate.value_objects.Title
import model.IEntity
import model.Id

class Blog(
    override val id: Id,
    var title: Title,
    private var content: Content,
    private var popularity: Popularity,
    private val time: Time
) : IEntity {
    private val tagIds = mutableSetOf<Id>()

    fun hasTag(tagId: Id): Boolean = tagIds.contains(tagId)

    fun add(tagId: Id) = tagIds.add(tagId)

    fun addAll(tagIds: Collection<Id>) = this.tagIds.addAll(tagIds)

    fun tagIds() = tagIds.toSet()

    fun changeTitle(title: Title) {
        this.title = title
    }

    fun changeContent(content: Content) {
        this.content = content
    }
}