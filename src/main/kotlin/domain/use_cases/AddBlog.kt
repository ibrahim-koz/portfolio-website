package domain.use_cases

import domain.aggregates.blog_aggregate.value_objects.Content
import domain.aggregates.blog_aggregate.value_objects.Title
import domain.aggregates.tag_aggregate.value_objects.Name
import domain.factories.*
import domain.repositories.IBlogRepository
import domain.repositories.ITagRepository
import domain.services.GetTagsOrCreateService
import domain.specifications.BlogAndTagMustBeAssociatedSpecification

class AddBlog(
    private val blogAggregateFactory: BlogAggregateFactory,
    private val blogRepository: IBlogRepository,
    private val tagRepository: ITagRepository,
    private val getTagsOrCreateService: GetTagsOrCreateService,
) {
    fun handle(addBlogCommand: AddBlogCommand): AddBlogResponse {
        try {
            val title: Title
            val content: Content
            val tagNames: Collection<Name>

            with(addBlogCommand) {
                title = titleAsValueObject()
                content = contentAsValueObject()
                tagNames = tagNamesAsValueObject()
            }

            val tags = getTagsOrCreateService.handle(tagNames)

            val blog = blogAggregateFactory
                .aBlog()
                .withTitle(title)
                .withContent(content)
                .created()
                .apply {
                    addAll(tags.map { it.id })
                }

            tags.forEach {
                it.attachTo(blog.id)
            }

            require(tags.all {
                BlogAndTagMustBeAssociatedSpecification().isSatisfiedBy(blog to it)
            })

            blogRepository.add(blog)
            tagRepository.addAll(tags)
            return AddBlogResponse()
        } catch (e: Exception) {
            return AddBlogResponse(e.message)
        }
    }
}
