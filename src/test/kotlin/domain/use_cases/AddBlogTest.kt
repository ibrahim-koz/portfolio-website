package domain.use_cases

import com.beust.klaxon.Klaxon
import domain.factories.*
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
        MockTagRepository()
    )
    @Test
    internal fun `should parse the given payload properly`() {
        val addBlogCommand = addBlogFactory.anAddBlogCommand(
            """
            {
                "title": "Cool Title",
                "content": [
                    {
                        "text": "Cool Text",
                        "style": "body"
                    },
                    {
                        "imagePath": "cool_path", 
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
                ContentElementField(text = "Cool Text", style = "body"),
                ContentElementField(imagePath = "cool_path", caption = "Cool Picture")
            ),
            listOf(TagField("Cool Tag"))
        )

        assertEquals(expectedAddBlogCommand, addBlogCommand)
    }

    @Test
    internal fun `should create a new blog`() {
        val addBlogCommand = addBlogFactory.anAddBlogCommand(
            """
            {
                "title": "Cool Title",
                "content": [
                    {
                        "text": "Cool Text",
                        "style": "body"
                    },
                    {
                        "imagePath": "cool_path", 
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
        assertNull(addBlogResponse.error)
    }
}