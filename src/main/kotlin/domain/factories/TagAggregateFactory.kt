package domain.factories

import domain.aggregates.tag_aggregate.value_objects.Name
import domain.factories.builders.TagBuilder
import utils.IdGenerator

class TagAggregateFactory(private val idGenerator: IdGenerator) {
    fun aTag(): TagBuilder {
        return TagBuilder(idGenerator)
    }

    fun aName(value: String): Name {
        return Name(value)
    }
}