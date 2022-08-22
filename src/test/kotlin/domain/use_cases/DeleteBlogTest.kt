package domain.use_cases

import com.beust.klaxon.Klaxon
import domain.factories.BlogAggregateFactory
import domain.factories.CommandFactory
import domain.factories.TagAggregateFactory
import domain.services.GetTagsOrCreateService
import infrastructure.MockBlogRepository
import infrastructure.MockTagRepository
import model.Id
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import utils.IdGenerator
import utils.TimeUtilityService


internal class DeleteBlogTest {
    private val blogRepository = MockBlogRepository()
    private val tagRepository = MockTagRepository()
    private val commandFactory = CommandFactory(Klaxon())
    private val deleteBlog = DeleteBlog(
        blogRepository,
        tagRepository,
    )

    private val addBlog = AddBlog(
        BlogAggregateFactory(IdGenerator(), TimeUtilityService()),
        blogRepository,
        tagRepository,
        GetTagsOrCreateService(TagAggregateFactory(IdGenerator()), tagRepository),
    )


    @BeforeEach
    internal fun setUp() {
        val addBlogCommand = commandFactory.anAddBlogCommand(
            """
            {
                "title": "Cool Title",
                "content": [
                    {
                        "type": "text",
                        "text": "Cool Text",
                        "style": "body"
                    },
                    {
                        "type": "image"
                        "path": "cool_path", 
                        "caption": "Cool Picture"
                    }
                ],
                "tags": [
                    {
                        "name": "Cool Tag"
                    }
                ]
            }
            """
        )

        addBlog.tryHandle(addBlogCommand)
    }

    @Test
    internal fun `should delete the blog with the given id`() {
        val deleteBlogCommand = commandFactory.aDeleteBlogCommand(
            """
            {
                "id": 0
            }
            """
        )


        deleteBlog.tryHandle(deleteBlogCommand)
        assertThrows<IllegalArgumentException> { blogRepository.get(Id(0)) }
    }
}