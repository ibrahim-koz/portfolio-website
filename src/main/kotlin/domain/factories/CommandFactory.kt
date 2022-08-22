package domain.factories

import com.beust.klaxon.Klaxon
import domain.commands.AddBlogCommand
import domain.commands.DeleteBlogCommand
import domain.commands.EditBlogCommand
import domain.commands.ReadBlogCommand

// TODO: we need to put klaxon out of the domain and may create new domain errors
class CommandFactory(
    private val klaxon: Klaxon
) {
    fun anAddBlogCommand(string: String): AddBlogCommand =
        requireNotNull(klaxon.parse<AddBlogCommand>(string))

    fun aDeleteBlogCommand(string: String): DeleteBlogCommand =
        requireNotNull(klaxon.parse<DeleteBlogCommand>(string))

    fun anEditBlogCommand(string: String): EditBlogCommand =
        requireNotNull(klaxon.parse<EditBlogCommand>(string))

    fun aReadBlogCommand(string: String): ReadBlogCommand =
        requireNotNull(klaxon.parse<ReadBlogCommand>(string))
}