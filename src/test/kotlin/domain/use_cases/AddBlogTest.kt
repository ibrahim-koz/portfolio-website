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