package domain.use_cases

import domain.aggregates.blog_aggregate.entities.Blog
import domain.repositories.IBlogRepository

class ListBlogs(
    private val blogRepository: IBlogRepository,
) {
    fun handle(): Collection<Blog> = blogRepository.getAll()
}