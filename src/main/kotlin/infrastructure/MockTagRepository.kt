package infrastructure

import domain.aggregates.tag_aggregate.entities.Tag
import domain.aggregates.tag_aggregate.value_objects.Name
import domain.exceptions.TagNotFoundException
import domain.repositories.ITagRepository
import model.Id

class MockTagRepository : ITagRepository {
    private val items = mutableMapOf<Id, Tag>()

    override fun get(id: Id): Tag {
        items[id]?.let { return it }
        throw TagNotFoundException()
    }

    override fun get(name: Name): Tag {
        try {
            return items.values.first { it.name == name }
        } catch (e: NoSuchElementException) {
            throw TagNotFoundException()
        }
    }

    override fun add(tag: Tag) {
        items[tag.id] = tag
    }

    override fun addAll(tags: Collection<Tag>) = items.putAll(tags.associateBy { it.id })

    override fun remove(tag: Tag) {
        if (!items.contains(tag.id)) throw TagNotFoundException()
        items.remove(tag.id)
    }

    override fun update(tag: Tag) {
        if (!items.contains(tag.id)) throw TagNotFoundException()
        items[tag.id] = tag
    }

    override fun contains(id: Id): Boolean = items.contains(id)

    override fun contains(name: Name): Boolean = items.values.map { it.name }.contains(name)
}