package domain.factories.builders

import domain.aggregates.tag_aggregate.entities.Tag
import domain.aggregates.tag_aggregate.value_objects.Name
import model.Id
import utils.IdGenerator

class TagBuilder(private val idGenerator: IdGenerator) {
    private lateinit var name: Name
    private var blogIds: MutableList<Id> = mutableListOf()
    fun named(name: Name): TagBuilder {
        this.name = name
        return this
    }

    fun attachedTo(blogId: Id): TagBuilder {
        blogIds.add(blogId)
        return this
    }

    fun created(): Tag {
        return Tag(idGenerator.generate(), name)
    }
}