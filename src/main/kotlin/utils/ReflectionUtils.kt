package utils

import kotlin.reflect.full.memberProperties
import kotlin.reflect.*
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor

fun createInstance(ownerClassName: String, vararg args: Any?): Any {
    val primaryConstructor = requireNotNull(Class.forName(ownerClassName).kotlin.primaryConstructor)
    return primaryConstructor.call(*args)
}

fun getValue(instance: Any, fieldName: String): Any? {
    val kClass = Class.forName(instance::class.qualifiedName).kotlin
    val member = kClass.memberProperties.firstOrNull { it.name == fieldName }
    requireNotNull(member)
    return member.getter.call(instance)
}


fun setValue(instance: Any, fieldName: String, value: Any?) {
    val kClass = Class.forName(instance::class.qualifiedName).kotlin
    val member = kClass.memberProperties.filterIsInstance<KMutableProperty<*>>()
        .firstOrNull { it.name == fieldName }
    member?.setter?.call(instance, value)
}


fun fieldsOf(instance: Any): List<String> {
    val kClass = Class.forName(instance::class.qualifiedName).kotlin
    val members = kClass.memberProperties
    return members.map { it.name }
}

val <T : Any> KClass<T>.constructorProperties
    get() =
        primaryConstructor?.let { ctor ->
            declaredMemberProperties.filter { prop ->
                ctor.parameters.any { param ->
                    param.name == prop.name
                            &&
                            param.type == prop.returnType
                }
            }
        } ?: emptyList()


fun getConstructorProperties(ownerClassName: String) =
    Class.forName(ownerClassName).kotlin.constructorProperties.map { it.name }
