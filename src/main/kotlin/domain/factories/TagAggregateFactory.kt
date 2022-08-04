package domain.factories

import domain.factories.builders.TagBuilder
import utils.IdGenerator

class TagAggregateFactory {
    fun aTag(): TagBuilder {
        return TagBuilder(IdGenerator())
    }
}