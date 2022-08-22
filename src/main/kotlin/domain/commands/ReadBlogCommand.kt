package domain.commands

import model.Id

class ReadBlogCommand(val id: Int) {
    fun idAsValueObject(): Id = Id(id)
}