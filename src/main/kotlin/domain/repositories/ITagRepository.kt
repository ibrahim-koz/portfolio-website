package domain.repositories

import domain.aggregates.tag_aggregate.entities.Tag
import domain.aggregates.tag_aggregate.value_objects.Name
import model.Id

interface ITagRepository {
    fun get(id: Id): Tag

    fun get(name: Name): Tag

    fun add(tag: Tag)

    fun remove(tag: Tag)

    fun update(tag: Tag)

    fun contains(id: Id): Boolean

    fun contains(name: Name): Boolean
}