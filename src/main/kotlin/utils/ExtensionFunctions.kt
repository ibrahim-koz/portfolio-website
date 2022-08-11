package utils


fun <K, V> Map<K, V?>.filterNotNullValues(): Map<K, V> =
    buildMap { for ((k, v) in this@filterNotNullValues) if (v != null) put(k, v) }

fun Any?.ignore() = Unit