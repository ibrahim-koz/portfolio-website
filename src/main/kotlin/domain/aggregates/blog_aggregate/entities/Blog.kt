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
    val tagIds: Collection<Id>,
    val popularity: Popularity,
    val time: Time
) : IEntity {
    fun hasTag(tagId: Id): Boolean = tagIds.contains(tagId)
}