package infrastructure

import domain.aggregates.blog_aggregate.entities.Blog
import domain.exceptions.BlogNotFoundException
import domain.repositories.IBlogRepository
import model.Id

class MockBlogRepository : IBlogRepository {
    private val items = mutableMapOf<Id, Blog>()

    override fun get(id: Id): Blog {
        items[id]?.let { return it }
        throw BlogNotFoundException()
    }

    override fun add(blog: Blog) {
        items[blog.id] = blog
    }

    override fun getAll(): Collection<Blog> = items.values.toList()

    override fun remove(blog: Blog) {
        if (!items.contains(blog.id)) throw BlogNotFoundException()
        items.remove(blog.id)
    }

    override fun update(blog: Blog) {
        if (!items.contains(blog.id)) throw BlogNotFoundException()
        items[blog.id] = blog
    }

    override fun contains(id: Id): Boolean = items.contains(id)
}