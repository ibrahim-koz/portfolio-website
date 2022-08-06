package infrastructure

import domain.aggregates.tag_aggregate.entities.Tag
import domain.aggregates.tag_aggregate.value_objects.Name
import domain.repositories.ITagRepository
import model.Id
import utils.requireNull

class MockTagRepository : ITagRepository {
    private val items = mutableMapOf<Id, Tag>()

    override fun get(id: Id): Tag = requireNotNull(items[id])
    override fun get(name: Name): Tag = requireNotNull(items.values.first { it.name == name })

    override fun add(tag: Tag) {
        requireNull(items[tag.id])
        items[tag.id] = tag
    }

    override fun remove(tag: Tag) {
        requireNotNull(items[tag.id])
        items.remove(tag.id)
    }

    override fun update(tag: Tag) {
        requireNotNull(items[tag.id])
        items[tag.id] = tag
    }

    override fun contains(id: Id): Boolean = items.contains(id)

    override fun contains(name: Name): Boolean = items.filterValues { it.name == name }.isNotEmpty()
}