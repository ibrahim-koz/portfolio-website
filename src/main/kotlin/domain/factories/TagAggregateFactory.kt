package domain.factories

import domain.builders.TagBuilder
import utils.IdGenerator

class TagAggregateFactory(private val idGenerator: IdGenerator) {
    fun aTag(): TagBuilder {
        return TagBuilder(idGenerator)
    }
}