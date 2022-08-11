package domain.use_cases

import domain.commands.DeleteBlogCommand
import domain.repositories.IBlogRepository
import domain.repositories.ITagRepository
import domain.responses.DeleteBlogResponse
import domain.specifications.BlogAndTagMustBeAssociatedSpecification
import model.Id

class DeleteBlog(
    private val blogRepository: IBlogRepository,
    private val tagRepository: ITagRepository,
) {
    fun handle(deleteBlogCommand: DeleteBlogCommand): DeleteBlogResponse {
        try {
            val id: Id

            with(deleteBlogCommand) {
                id = idAsValueObject()
            }

            val blog = blogRepository.get(id)

            val tags = blog.tagIds().map { tagRepository.get(it) }

            tags.forEach {
                it.detach(blog.id)
            }

            require(tags.all {
                BlogAndTagMustBeAssociatedSpecification().not().isSatisfiedBy(blog to it)
            })

            blogRepository.remove(blog)
            tagRepository.addAll(tags)
            return DeleteBlogResponse()
        } catch (e: Exception) {
            return DeleteBlogResponse(e.message)
        }
    }
}