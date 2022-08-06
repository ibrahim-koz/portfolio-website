package domain.repositories

import domain.aggregates.blog_aggregate.entities.Blog
import model.Id

interface IBlogRepository {
    fun get(id: Id): Blog

    fun add(blog: Blog)

    fun remove(blog: Blog)

    fun update(blog: Blog)

    fun contains(id: Id): Boolean
}