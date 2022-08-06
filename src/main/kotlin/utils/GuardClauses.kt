package utils

fun <T> requireNull(value: T?) = require(value == null)