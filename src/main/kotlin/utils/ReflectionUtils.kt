package utils

import kotlin.reflect.full.createInstance
import kotlin.reflect.full.memberProperties
import kotlin.reflect.*

fun createInstance(ownerClassName: String): Any? {
    val kClass = Class.forName(ownerClassName).kotlin
    return kClass.createInstance()
}

fun getValue(instance: Any, fieldName: String): Any? {
    val kClass = Class.forName(instance::class.simpleName).kotlin
    val member = kClass.memberProperties.filterIsInstance<KMutableProperty<*>>()
        .firstOrNull { it.name == fieldName }
    requireNotNull(member)
    return member.getter.call(instance)
}


fun setValue(instance: Any, fieldName: String, value: Any?) {
    val kClass = Class.forName(instance::class.simpleName).kotlin
    val member = kClass.memberProperties.filterIsInstance<KMutableProperty<*>>()
        .firstOrNull { it.name == fieldName }
    member?.setter?.call(instance, value)
}


fun nonNullFieldsOf(instance: Any): List<String> {
    val kClass = Class.forName(instance::class.simpleName).kotlin
    val members = kClass.memberProperties.filterIsInstance<KMutableProperty<*>>()
    return members.filter { isNull(it.getter.call(instance)) }.map { it.name }
}