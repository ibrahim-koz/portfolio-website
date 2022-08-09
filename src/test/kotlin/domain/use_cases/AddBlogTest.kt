package domain.use_cases

import com.beust.klaxon.Klaxon
import domain.factories.*
import domain.services.GetTagsOrCreateService
import infrastructure.MockBlogRepository
import infrastructure.MockTagRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import utils.IdGenerator
import utils.TimeUtilityService

internal class AddBlogTest {
    private val addBlogFactory = AddBlogFactory(Klaxon())
    private val addBlog = AddBlog(
        BlogAggregateFactory(IdGenerator(), TimeUtilityService()),
        TagAggregateFactory(IdGenerator()),
        MockBlogRepository(),
        MockTagRepository(),
        GetTagsOrCreateService(),
    )

    @Test
    internal fun `should parse the given payload properly`() {
        val addBlogCommand = addBlogFactory.anAddBlogCommand(
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

        val expectedAddBlogCommand = AddBlogCommand(
            "Cool Title",
            listOf(
                ContentElementField(type = "text").apply {
                    text = "Cool Text"
                    style = "body"
                },
                ContentElementField(type = "image").apply {
                    path = "cool_path"
                    caption = "Cool Picture"
                }
            ),
            listOf(TagField("Cool Tag"))
        )

        assertEquals(expectedAddBlogCommand, addBlogCommand)
    }

    @Test
    internal fun `should create proper value objects from the payload`() {
        val addBlogCommand = addBlogFactory.anAddBlogCommand(
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


        val content = addBlogCommand.content()
        println("anan")
    }

    @Test
    internal fun `should create a new blog`() {
        val addBlogCommand = addBlogFactory.anAddBlogCommand(
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

//        val addBlogResponse = addBlog.handle(addBlogCommand)
//        assertNull(addBlogResponse.error)
    }
}