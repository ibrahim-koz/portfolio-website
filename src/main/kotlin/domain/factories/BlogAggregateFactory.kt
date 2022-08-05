package domain.factories

import domain.aggregates.blog_aggregate.value_objects.*
import domain.factories.builders.BlogBuilder
import domain.factories.builders.ContentBuilder
import utils.IdGenerator
import utils.TimeUtilityService

class BlogAggregateFactory(private val idGenerator: IdGenerator, private val timeUtilityService: TimeUtilityService) {
    fun aBlog(): BlogBuilder {
        return BlogBuilder(idGenerator, timeUtilityService)
    }

    fun aTitle(value: String): Title {
        return Title(value)
    }

    fun aContent(): ContentBuilder {
        return ContentBuilder()
    }

    fun aTextElement(text: String, style: String): ContentElement {
        return TextElement(Text(text), Style(style))
    }

    fun anImageElement(imagePath: String, caption: String): ImageElement {
        return ImageElement(Path(imagePath), Text(caption))
    }
}