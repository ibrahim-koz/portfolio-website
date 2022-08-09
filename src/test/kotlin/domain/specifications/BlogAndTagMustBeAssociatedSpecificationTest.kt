package domain.specifications

import domain.aggregates.blog_aggregate.value_objects.Content
import domain.aggregates.blog_aggregate.value_objects.Title
import domain.aggregates.tag_aggregate.value_objects.Name
import domain.factories.BlogAggregateFactory
import domain.factories.TagAggregateFactory
import org.junit.jupiter.api.Test
import utils.IdGenerator
import utils.TimeUtilityService

internal class BlogAndTagMustBeAssociatedSpecificationTest {
    private val idGenerator = IdGenerator()
    private val blogAggregateFactory = BlogAggregateFactory(idGenerator, TimeUtilityService())
    private val tagAggregateFactory = TagAggregateFactory(idGenerator)

    @Test
    internal fun `should turn out that the blog and the tags is associated`() {
        val blog = blogAggregateFactory.aBlog().withTitle(Title("Cool Title")).withContent(Content()).created()
        val tag = tagAggregateFactory.aTag().named(Name("Cool Tag")).created().apply { attachTo(blog.id) }
        blog.add(tag.id)

        assert(BlogAndTagMustBeAssociatedSpecification().isSatisfiedBy(blog, tag))
    }
}