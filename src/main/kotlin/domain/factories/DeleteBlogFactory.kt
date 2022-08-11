package domain.factories

import com.beust.klaxon.Klaxon
import model.Id

// TODO: we can merge different use case factories into one.

class DeleteBlogFactory(
    private val klaxon: Klaxon
) {
    fun aDeleteBlogCommand(string: String): DeleteBlogCommand =
        requireNotNull(klaxon.parse<DeleteBlogCommand>(string))
}

data class DeleteBlogCommand(val id: Int) {
    fun idAsValueObject(): Id = Id(id)
}

class DeleteBlogResponse(val error: String? = null)
