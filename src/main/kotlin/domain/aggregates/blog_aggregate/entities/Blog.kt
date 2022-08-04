package domain.aggregates.blog_aggregate.entities

import domain.aggregates.blog_aggregate.value_objects.Popularity
import domain.aggregates.blog_aggregate.value_objects.Time
import domain.aggregates.blog_aggregate.value_objects.Title
import model.IEntity
import model.Id
import javax.swing.text.AbstractDocument.Content

class Blog(
    override val id: Id,
    val title: Title,
    val content: Content,
    val tagId: Id,
    val popularity: Popularity,
    val time: Time
): IEntity