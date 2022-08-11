package domain.factories

import com.beust.klaxon.Klaxon
import domain.commands.AddBlogCommand
import domain.commands.DeleteBlogCommand
import domain.commands.EditBlogCommand

class CommandFactory(
    private val klaxon: Klaxon
) {
    fun anAddBlogCommand(string: String): AddBlogCommand =
        requireNotNull(klaxon.parse<AddBlogCommand>(string))

    fun aDeleteBlogCommand(string: String): DeleteBlogCommand =
        requireNotNull(klaxon.parse<DeleteBlogCommand>(string))

    fun anEditBlogCommand(string: String): EditBlogCommand =
        requireNotNull(klaxon.parse<EditBlogCommand>(string))
}