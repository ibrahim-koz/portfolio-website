package domain.factories

import domain.factories.builders.BlogBuilder
import utils.IdGenerator
import utils.TimeUtilityService

class BlogAggregateFactory(private val idGenerator: IdGenerator, private val timeUtilityService: TimeUtilityService) {
    fun aBlog(): BlogBuilder {
        return BlogBuilder(idGenerator, timeUtilityService)
    }
}