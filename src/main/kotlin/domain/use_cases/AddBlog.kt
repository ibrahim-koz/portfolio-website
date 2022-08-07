package domain.use_cases

import domain.aggregates.blog_aggregate.entities.Blog
import domain.aggregates.blog_aggregate.value_objects.Content
import domain.aggregates.blog_aggregate.value_objects.TextElement
import domain.aggregates.tag_aggregate.value_objects.Name
import domain.factories.*
import domain.factories.builders.BlogBuilder
import domain.repositories.IBlogRepository
import domain.repositories.ITagRepository
import domain.services.GetTagsOrCreateService
import domain.specifications.BlogAndTagMustBeAssociatedSpecification
import domain.type_casting_utils.isImageElement
import domain.type_casting_utils.isTextElement

class AddBlog(
    private val blogAggregateFactory: BlogAggregateFactory,
    private val tagAggregateFactory: TagAggregateFactory,
    private val blogRepository: IBlogRepository,
    private val tagRepository: ITagRepository,
    private val getTagsOrCreateService: GetTagsOrCreateService,
) {
//    fun handle(addBlogCommand: AddBlogCommand): AddBlogResponse {
//        try {
//            // If we introduce Content and Tags data instead of just simple mapping, we can encapsulate those
//            // functionalities into it.
//
//            // we can improve it through destructuring mechanism after research
//            val tagNames = addBlogCommand.tagNames()
//            val title = addBlogCommand.title()
//            val content = addBlogCommand.content()
//
//            val blog = blogAggregateFactory.aBlog().withTitle(title).withContent(content).created()
//
//            val tags = getTagsOrCreateService.handle(tagNames)
//
//            tags.forEach {
//                it.addBlogId(blog.id)
//            }
//
//            require(tags.all {
//                BlogAndTagMustBeAssociatedSpecification().isSatisfiedBy(blog, it)
//            })
//
//            blogRepository.add(blog)
//            tagRepository.addAll(tags)
//            return AddBlogResponse()
//        } catch (e: Exception) {
//            return AddBlogResponse(e.message)
//        }
//    }
//
//    private fun List<TagField>.toTags() =
//        map { tagAggregateFactory.aTag().named(tagAggregateFactory.aName(it.name)).created() }
//
//    private fun createBlog(addBlogCommand: AddBlogCommand): Blog {
//        val title = addBlogCommand.title()
//        val contentElements = addBlogCommand.content.map {
//            // We need to figure out the following branching with a more elegant solution.
//            // TODO: !! notation is not going to be used in future.
//            if (isTextElement(it)) TextElement(it.text!!, it.style!!)
//            else if (isImageElement(it)) blogAggregateFactory.anImageElement(it.imagePath!!, it.caption!!)
//            else throw IllegalStateException()
//        }
//        val content = Content(contentElements)
//        return
//    }
//
//    private fun locateExistentAndAbsentTagsFields(tagNames: Collection<Name>): Pair<List<Name>, List<Name>> {
//        return tagNames.partition { tagRepository.contains(it) }
//    }
}