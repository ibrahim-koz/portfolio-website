package domain.aggregates.blog_aggregate.entities

import domain.aggregates.blog_aggregate.value_objects.Content
import domain.aggregates.blog_aggregate.value_objects.Popularity
import domain.aggregates.blog_aggregate.value_objects.Time
import domain.aggregates.blog_aggregate.value_objects.Title
import model.IEntity
import model.Id

class Blog(
    override val id: Id,
    val title: Title,
    val content: Content,
    val popularity: Popularity,
    val time: Time
) : IEntity {
    private val tagIds = mutableSetOf<Id>()
    fun hasTag(tagId: Id): Boolean = tagIds.contains(tagId)

    fun add(tagId: Id) = tagIds.add(tagId)

    fun addAll(tagIds: Collection<Id>) = this.tagIds.addAll(tagIds)
}