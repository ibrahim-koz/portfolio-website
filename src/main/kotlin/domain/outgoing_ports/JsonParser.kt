package domain.outgoing_ports

import kotlin.reflect.KClass

interface JsonParser {
    fun <T: Any> parse(string: String, type: KClass<T>): T
}


inline fun <reified T : Any> JsonParser.parse(string: String) = parse(string, T::class)