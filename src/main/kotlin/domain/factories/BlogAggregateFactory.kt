package domain.factories

import domain.factories.builders.BlogBuilder
import utils.IdGenerator
import utils.TimeUtilityService

class BlogAggregateFactory {
    fun aBlog(): BlogBuilder{
        return BlogBuilder(IdGenerator(), TimeUtilityService())
    }
}