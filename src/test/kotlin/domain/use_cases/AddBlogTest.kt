package domain.use_cases

import com.beust.klaxon.Klaxon
import domain.aggregates.blog_aggregate.value_objects.ContentElement
import domain.factories.AddBlogCommand
import domain.factories.AddBlogFactory
import domain.factories.ContentElementField
import domain.factories.TagField
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class AddBlogTest {
    private val addBlogFactory = AddBlogFactory(Klaxon())

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
                """.trimIndent()
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
//        val addBlogCommand = addBlogFactory
//            .anAddBlogCommand()
//            .withPayload(
//                """
//            {
//                title: Cool Title,
//                content: [
//                    {text: Cool Text, style: body},
//                    {picture: path, caption: Cool Picture}
//                ],
//                tags: [Cool Tag]
//            }
//                """.trimIndent()
//            )
//            .created()
//
//        val addBlogResponse = AddBlog().handle(addBlogCommand)
//        assertNull(addBlogResponse.error)
    }
}