package domain.repositories

import domain.aggregates.tag_aggregate.entities.Tag
import model.Id

interface ITagRepository {
    fun get(tagId: Id)

    fun add(tag: Tag)

    fun remove(tag: Tag)

    fun update(tag: Tag)
}