package domain.repositories

import domain.aggregates.blog_aggregate.entities.Blog
import model.Id

interface IBlogRepository {
    fun get(blogId: Id)

    fun add(blog: Blog)

    fun remove(blog: Blog)

    fun update(blog: Blog)
}