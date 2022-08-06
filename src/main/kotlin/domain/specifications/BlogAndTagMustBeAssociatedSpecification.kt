package domain.specifications

import domain.aggregates.blog_aggregate.entities.Blog
import domain.aggregates.tag_aggregate.entities.Tag

class BlogAndTagMustBeAssociatedSpecification {
    fun isSatisfiedBy(blog: Blog, tag: Tag): Boolean = blog.hasTag(tag.id) && tag.isAttachedToBlog(blog.id)
}