package domain.services

import domain.aggregates.tag_aggregate.value_objects.Name
import domain.factories.TagAggregateFactory
import infrastructure.MockTagRepository
import org.junit.jupiter.api.Test
import utils.IdGenerator

internal class GetTagsOrCreateServiceTest {
    private val tagRepository = MockTagRepository()
    private val tagAggregateFactory = TagAggregateFactory(IdGenerator())
    private val getTagsOrCreateService = GetTagsOrCreateService(tagAggregateFactory, tagRepository)

    @Test
    internal fun `should create missing tags for given tag names`() {
        val absentTagNames = listOf(Name("Tag1"), Name("Tag2"))
        val existingTagNames = listOf(Name("Tag3"))
        val allTagNames = absentTagNames + existingTagNames

        existingTagNames.forEach {
            tagRepository.add(
                tagAggregateFactory.aTag().named(it).created()
            )
        }

        getTagsOrCreateService.handle(allTagNames)
        assert(allTagNames.map { tagRepository.contains(it) }.all { it })
    }
}