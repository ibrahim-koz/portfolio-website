package domain.use_cases

import domain.aggregates.blog_aggregate.value_objects.Content
import domain.aggregates.blog_aggregate.value_objects.Title
import domain.aggregates.tag_aggregate.value_objects.Name
import domain.commands.EditBlogCommand
import domain.repositories.IBlogRepository
import domain.repositories.ITagRepository
import domain.responses.EditBlogResponse
import domain.services.GetTagsOrCreateService
import domain.specifications.BlogAndTagMustBeAssociatedSpecification
import model.Id
import utils.*

// TODO: I decided to not swallow the error for debugging purposes.
class EditBlog(
    private val blogRepository: IBlogRepository,
    private val tagRepository: ITagRepository,
    private val getTagsOrCreateService: GetTagsOrCreateService,
) {

    fun handle(editBlogCommand: EditBlogCommand): EditBlogResponse {
        val id: Id
        val title: Title?
        val content: Content?
        val tagNames: Collection<Name>?


        with(editBlogCommand) {
            id = idAsValueObject()
            title = titleAsValueObject()
            content = contentAsValueObject()
            tagNames = tagNamesAsValueObject()
        }

        tagNames?.let { getTagsOrCreateService.handle(it) }.ignore()

        val blog = blogRepository.get(id)

        val previousTags = blog.tagIds().map { tagRepository.get(it) }.toSet()

        val newTags = tagNames?.map { tagRepository.get(it) }?.toSet() ?: setOf()

        val tagsToDetach = previousTags - newTags

        val tagsToAttach = newTags - previousTags

        blog.apply {
            title?.let {
                changeTitle(it)
            }

            content?.let {
                changeContent(it)
            }

            addAll(tagsToAttach.map { it.id })
        }

        tagsToAttach.forEach {
            it.attachTo(blog.id)
        }

        tagsToDetach.forEach {
            it.detach(blog.id)
        }

        require(tagsToAttach.all {
            BlogAndTagMustBeAssociatedSpecification().isSatisfiedBy(blog to it)

        })

        require(tagsToDetach.all {
            BlogAndTagMustBeAssociatedSpecification().not().isSatisfiedBy(blog to it)
        })

        blogRepository.update(blog)
        tagRepository.addAll(newTags)
        previousTags.forEach {
            tagRepository.update(it)
        }

        return EditBlogResponse()
    }
}