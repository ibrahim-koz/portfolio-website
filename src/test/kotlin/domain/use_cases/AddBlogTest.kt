package domain.use_cases

import com.beust.klaxon.Klaxon
import domain.factories.*
import domain.services.GetTagsOrCreateService
import infrastructure.MockBlogRepository
import infrastructure.MockTagRepository
import model.Id
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import utils.IdGenerator
import utils.TimeUtilityService

internal class AddBlogTest {
    private val tagAggregateFactory = TagAggregateFactory(IdGenerator())
    private val tagRepository = MockTagRepository()
    private val commandFactory = CommandFactory(Klaxon())
    private val blogRepository = MockBlogRepository()
    private val addBlog = AddBlog(
        BlogAggregateFactory(IdGenerator(), TimeUtilityService()),
        blogRepository,
        tagRepository,
        GetTagsOrCreateService(tagAggregateFactory, tagRepository),
    )

    @Test
    internal fun `should create a new blog`() {
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

        assertDoesNotThrow { addBlog.handle(addBlogCommand) }
        assertDoesNotThrow { blogRepository.get(Id(0)) }
        assertDoesNotThrow { tagRepository.get(Id(0))}
    }
}