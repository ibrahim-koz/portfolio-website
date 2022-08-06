package infrastructure

import domain.aggregates.blog_aggregate.entities.Blog
import domain.repositories.IBlogRepository
import model.Id
import utils.requireNull

class MockBlogRepository : IBlogRepository {
    private val items = mutableMapOf<Id, Blog>()

    override fun get(id: Id): Blog = requireNotNull(items[id])

    override fun add(blog: Blog) {
        requireNull(items[blog.id])
        items[blog.id] = blog
    }

    override fun remove(blog: Blog) {
        requireNotNull(items[blog.id])
        items.remove(blog.id)
    }

    override fun update(blog: Blog) {
        requireNotNull(items[blog.id])
        items[blog.id] = blog
    }

    override fun contains(id: Id): Boolean = items.contains(id)
}