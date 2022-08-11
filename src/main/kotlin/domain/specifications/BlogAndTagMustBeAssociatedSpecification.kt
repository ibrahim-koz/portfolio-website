package domain.specifications

import Specification
import domain.aggregates.blog_aggregate.entities.Blog
import domain.aggregates.tag_aggregate.entities.Tag

class BlogAndTagMustBeAssociatedSpecification : Specification<Pair<Blog, Tag>>() {
    override fun isSatisfiedBy(candidate: Pair<Blog, Tag>): Boolean {
        val (blog, tag) = candidate
        return blog.hasTag(tag.id) && tag.isAttachedTo(blog.id)
    }
}