package domain.services

import domain.aggregates.tag_aggregate.entities.Tag
import domain.aggregates.tag_aggregate.value_objects.Name
import domain.factories.TagAggregateFactory
import domain.repositories.ITagRepository

class GetTagsOrCreateService(
    private val tagAggregateFactory: TagAggregateFactory,
    private val tagRepository: ITagRepository
) {
    fun handle(tagNames: Collection<Name>): List<Tag> {
        val (existingTagsNames, absentTagsNames) = tagNames
            .associateWith { tagRepository.contains(it) }
            .toList()
            .partition { it.second }
            .run {
                first.map { it.first } to second.map { it.first }
            }

        val existingTags = existingTagsNames.map { tagRepository.get(it) }
        val absentTags = absentTagsNames.map { tagAggregateFactory.aTag().named(it).created() }

        existingTags.forEach {
            tagRepository.update(it)
        }

        tagRepository.addAll(absentTags)

        return existingTags + absentTags
    }
}