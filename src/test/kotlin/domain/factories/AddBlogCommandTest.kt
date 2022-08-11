package domain.factories

import com.beust.klaxon.Klaxon
import domain.aggregates.blog_aggregate.value_objects.*
import domain.aggregates.tag_aggregate.value_objects.Name
import domain.commands.AddBlogCommand
import domain.commands.ContentElementField
import domain.commands.TagField
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class AddBlogCommandTest {
    private val commandFactory = CommandFactory(Klaxon())

    @Test
    internal fun `should parse the given payload properly`() {
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

        val actualAddBlogCommand = commandFactory.anAddBlogCommand(
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

        assertEquals(expectedAddBlogCommand, actualAddBlogCommand)
    }

    @Test
    internal fun `should create proper value objects from the payload`() {
        val expectedTitle = Title("Cool Title")
        val expectedContent = Content().apply {
            addContentElements(
                TextElement(Text("Cool Text"), Style("body")),
                ImageElement(Path("cool_path"), Caption("Cool Picture"))
            )
        }
        val expectedTagNames = listOf(Name("Cool Tag"))

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

        val actualTitle = addBlogCommand.titleAsValueObject()
        val actualContent = addBlogCommand.contentAsValueObject()
        val actualTagNames = addBlogCommand.tagNamesAsValueObject()


        assertEquals(expectedTitle, actualTitle)
        assertEquals(expectedContent, actualContent)
        assertEquals(expectedTagNames, actualTagNames)
    }
}