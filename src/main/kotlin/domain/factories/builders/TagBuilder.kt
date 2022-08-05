package domain.factories.builders

import domain.aggregates.tag_aggregate.entities.Tag
import domain.aggregates.tag_aggregate.value_objects.Name
import model.Id
import utils.IdGenerator

class TagBuilder(private val idGenerator: IdGenerator) {
    private lateinit var name: Name
    private lateinit var blogIds: MutableList<Id>
    fun named(name: Name): TagBuilder {
        this.name = name
        return this
    }

    fun attachedTo(blogId: Id): TagBuilder {
        blogIds = mutableListOf(blogId)
        return this
    }

    fun created(): Tag {
        return Tag(idGenerator.generate(), name, blogIds)
    }
}