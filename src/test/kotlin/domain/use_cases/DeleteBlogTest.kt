package domain.use_cases

import com.beust.klaxon.Klaxon
import domain.factories.BlogAggregateFactory
import domain.factories.CommandFactory
import domain.factories.TagAggregateFactory
import domain.services.GetTagsOrCreateService
import infrastructure.MockBlogRepository
import infrastructure.MockTagRepository
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.IdGenerator
import utils.TimeUtilityService


internal class DeleteBlogTest {
    private val mockBlogRepository = MockBlogRepository()
    private val mockTagRepository = MockTagRepository()
    private val commandFactory = CommandFactory(Klaxon())
    private val deleteBlog = DeleteBlog(
        mockBlogRepository,
        mockTagRepository,
    )

    private val addBlog = AddBlog(
        BlogAggregateFactory(IdGenerator(), TimeUtilityService()),
        mockBlogRepository,
        mockTagRepository,
        GetTagsOrCreateService(TagAggregateFactory(IdGenerator()), mockTagRepository),
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

        val addBlogResponse = addBlog.handle(addBlogCommand)
        addBlogResponse.error?.let {
            throw Exception("An error has occurred during the setup, with message: $it")
        }
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


        val deleteBlogResponse = deleteBlog.handle(deleteBlogCommand)
        assertNull(deleteBlogResponse.error)
    }
}