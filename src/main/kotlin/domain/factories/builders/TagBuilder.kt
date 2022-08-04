package domain.factories.builders

import domain.aggregates.tag_aggregate.entities.Tag
import model.Id
import utils.IdGenerator

class TagBuilder(private val idGenerator: IdGenerator) {
    private lateinit var blogIds: MutableList<Id>

    fun attachedTo(blogId: Id): TagBuilder {
        blogIds = mutableListOf(blogId)
        return this
    }

    fun created(): Tag {
        return Tag(idGenerator.generate(), blogIds)
    }
}