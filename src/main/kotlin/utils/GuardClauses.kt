package utils

fun <T> isNull(value: T?) = value == null

fun <T> requireNull(value: T?) = require(isNull(value))