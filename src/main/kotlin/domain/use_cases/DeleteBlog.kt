package domain.use_cases

import domain.commands.DeleteBlogCommand
import domain.repositories.IBlogRepository
import domain.repositories.ITagRepository
import domain.specifications.BlogAndTagMustBeAssociatedSpecification
import model.Id
import java.lang.Exception

class DeleteBlog(
    private val blogRepository: IBlogRepository,
    private val tagRepository: ITagRepository,
) {
    fun tryHandle(deleteBlogCommand: DeleteBlogCommand) {
        try {
            handle(deleteBlogCommand)
        } catch (e: Exception) {
            recover()
            throw e
        }
    }

    private fun handle(deleteBlogCommand: DeleteBlogCommand) {
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
        tags.forEach {
            tagRepository.update(it)
        }
    }

    private fun recover() {

    }
}