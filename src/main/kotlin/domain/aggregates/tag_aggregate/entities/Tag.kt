package domain.aggregates.tag_aggregate.entities

import model.IEntity
import model.Id
import domain.aggregates.tag_aggregate.value_objects.Name

// TODO: we should return a copy of the value objects when accessed from outside.
class Tag(override val id: Id, val name: Name) : IEntity {
    private val blogIds = mutableSetOf<Id>()
    fun isAttachedTo(blogId: Id): Boolean = blogIds.contains(blogId)
    fun attachTo(blogId: Id) = blogIds.add(blogId)
    fun detach(blogId: Id) = blogIds.remove(blogId)
}