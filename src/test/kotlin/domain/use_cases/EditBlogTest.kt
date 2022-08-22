package domain.use_cases

import com.beust.klaxon.Klaxon
import domain.aggregates.blog_aggregate.value_objects.Title
import domain.factories.BlogAggregateFactory
import domain.factories.CommandFactory
import domain.factories.TagAggregateFactory
import domain.services.GetTagsOrCreateService
import infrastructure.MockBlogRepository
import infrastructure.MockTagRepository
import model.Id
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.IdGenerator
import utils.TimeUtilityService

internal class EditBlogTest {
    private val blogRepository = MockBlogRepository()
    private val tagRepository = MockTagRepository()
    private val commandFactory = CommandFactory(Klaxon())
    private val getTagsOrCreateService = GetTagsOrCreateService(TagAggregateFactory(IdGenerator()), tagRepository)

    private val editBlog = EditBlog(blogRepository, tagRepository, getTagsOrCreateService)
    private val addBlog = AddBlog(
        BlogAggregateFactory(IdGenerator(), TimeUtilityService()),
        blogRepository,
        tagRepository,
        getTagsOrCreateService,
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
    internal fun `should edit the blog with the given id according to the provided fields`() {
        val editBlogCommand = commandFactory.anEditBlogCommand(
            """
            {
                "id": 0,
                "title": "Edited Title",
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

        editBlog.tryHandle(editBlogCommand)
        val blog = blogRepository.get(Id(0))
        assertEquals(Title("Edited Title"), blog.title)
    }
}