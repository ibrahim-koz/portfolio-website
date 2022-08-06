package domain.use_cases

import domain.factories.AddBlogCommand
import domain.factories.AddBlogResponse
import domain.factories.BlogAggregateFactory
import domain.factories.TagAggregateFactory
import domain.repositories.IBlogRepository
import domain.repositories.ITagRepository

class AddBlog(
    private val blogAggregateFactory: BlogAggregateFactory,
    private val tagAggregateFactory: TagAggregateFactory,
    private val blogRepository: IBlogRepository,
    private val tagRepository: ITagRepository
) {
    fun handle(addBlogCommand: AddBlogCommand): AddBlogResponse {
        // create the corresponding objects from command

        // locate already existing tags

        // create missing tags

        // execute the following specification

        // persist both new blog and tags, update the already existing tags
        TODO()
    }
}