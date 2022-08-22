package domain.use_cases

import com.beust.klaxon.Klaxon
import domain.factories.BlogAggregateFactory
import domain.factories.CommandFactory
import domain.factories.TagAggregateFactory
import domain.services.GetTagsOrCreateService
import infrastructure.MockBlogRepository
import infrastructure.MockTagRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.IdGenerator
import utils.TimeUtilityService

internal class ListBlogsTest {
    private val tagAggregateFactory = TagAggregateFactory(IdGenerator())
    private val tagRepository = MockTagRepository()
    private val commandFactory = CommandFactory(Klaxon())
    private val blogRepository = MockBlogRepository()
    private val listBlogs = ListBlogs(blogRepository)
    private val addBlog = AddBlog(
        BlogAggregateFactory(IdGenerator(), TimeUtilityService()),
        blogRepository,
        tagRepository,
        GetTagsOrCreateService(tagAggregateFactory, tagRepository),
    )

    @BeforeEach
    internal fun setUp() {
        val addBlogCommands = listOf(
            commandFactory.anAddBlogCommand(
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
            ),
            commandFactory.anAddBlogCommand(
                """
            {
                "title": "Way Cooler Title",
                "content": [
                    {
                        "type": "text",
                        "text": "Cooler Text",
                        "style": "quote"
                    },
                    {
                        "type": "codeSnippet"
                        "body": "console.log(\"hello\")", 
                        "language": "JavaScript"
                    }
                ],
                "tags": [
                    {
                        "name": "Way Cooler Tag"
                    }
                ]
            }
            """
            )
        )

        addBlogCommands.forEach {
            addBlog.handle(it)
        }
    }

    @Test
    internal fun `should list all blogs`() {
        val blogs = listBlogs.handle()
        assertEquals(blogs.size, 2)
    }
}

