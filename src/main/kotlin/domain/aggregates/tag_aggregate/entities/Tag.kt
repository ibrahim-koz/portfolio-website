package domain.aggregates.tag_aggregate.entities

import model.IEntity
import model.Id

class Tag(override val id: Id, val blogIds: Collection<Id>) : IEntity {
}