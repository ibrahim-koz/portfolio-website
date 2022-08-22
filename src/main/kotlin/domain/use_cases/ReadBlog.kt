package domain.use_cases

import domain.commands.ReadBlogCommand
import domain.repositories.IBlogRepository

class ReadBlog(
    private val blogRepository: IBlogRepository
) {
   fun handle(readBlogCommand: ReadBlogCommand) = blogRepository.get(readBlogCommand.idAsValueObject())
}