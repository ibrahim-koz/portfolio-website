package domain.commands

data class TagField(val name: String)

data class ContentElementField(
    val type: String,
) {
    lateinit var text: String
    lateinit var style: String

    lateinit var path: String
    lateinit var caption: String

    lateinit var language: String
    lateinit var body: String
}