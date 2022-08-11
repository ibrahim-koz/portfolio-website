package domain.commands

import model.Id

data class DeleteBlogCommand(val id: Int) {
    fun idAsValueObject(): Id = Id(id)
}