package domain.aggregates.tag_aggregate.entities

import model.IEntity
import model.Id
import domain.aggregates.tag_aggregate.value_objects.Name

class Tag(override val id: Id, val name: Name, var blogIds: Collection<Id>) : IEntity {
}