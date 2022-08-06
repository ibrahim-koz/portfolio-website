package domain.use_cases

import domain.aggregates.tag_aggregate.value_objects.Name
import domain.factories.*
import domain.factories.builders.BlogBuilder
import domain.repositories.IBlogRepository
import domain.repositories.ITagRepository
import domain.specifications.BlogAndTagMustBeAssociatedSpecification
import domain.type_casting_utils.isImageElement
import domain.type_casting_utils.isTextElement
import model.Id

class AddBlog(
    private val blogAggregateFactory: BlogAggregateFactory,
    private val tagAggregateFactory: TagAggregateFactory,
    private val blogRepository: IBlogRepository,
    private val tagRepository: ITagRepository
) {
    fun handle(addBlogCommand: AddBlogCommand): AddBlogResponse {
        try {

            // tagFieldlari ayirma isini sevmedim

            val (existentTagsFields, absentTagsFields) = locateExistentAndAbsentTagsFields(addBlogCommand.tags)


            val existentTags = existentTagsFields.map { tagRepository.get(tagAggregateFactory.aName(it.name)) }
            val absentTags = absentTagsFields.toTags()

            val blog = createBlog(addBlogCommand)
                .havingTags(absentTags.map { it.id })
                .havingTags(existentTags.map { it.id })
                .created()

            val tags = existentTags + absentTags
            tags.forEach {
                it.blogIds = mutableListOf<Id>().apply {
                    addAll(it.blogIds + blog.id)
                }
            }



            require(tags.all {
                BlogAndTagMustBeAssociatedSpecification().isSatisfiedBy(blog, it)
            })


            blogRepository.add(blog)
            tagRepository.addAll(tags)
            return AddBlogResponse()
        } catch (e: Exception) {
            return AddBlogResponse(e.message)
        }
    }

    private fun List<TagField>.toTags() =
        map { tagAggregateFactory.aTag().named(tagAggregateFactory.aName(it.name)).created() }

    private fun createBlog(addBlogCommand: AddBlogCommand): BlogBuilder {
        val title = blogAggregateFactory.aTitle(addBlogCommand.title)
        val contentElements = addBlogCommand.content.map {
            // TODO: !! notation is not going to be used in future.
            if (isTextElement(it)) blogAggregateFactory.aTextElement(it.text!!, it.style!!)
            else if (isImageElement(it)) blogAggregateFactory.anImageElement(it.imagePath!!, it.caption!!)
            else throw IllegalStateException()
        }
        val content = blogAggregateFactory.aContent().havingContentElements(contentElements)
        return blogAggregateFactory.aBlog().withTitle(title).withContent(content)
    }

    private fun locateExistentAndAbsentTagsFields(tagFields: Collection<TagField>): Pair<List<TagField>, List<TagField>> {
        return tagFields.partition { tagRepository.contains(Name(it.name)) }
    }
}