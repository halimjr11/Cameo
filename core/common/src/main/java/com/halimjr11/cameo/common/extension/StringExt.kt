package com.halimjr11.cameo.common.extension

fun String.capitalizeWords(): String =
    split(" ").joinToString(" ") { it.replaceFirstChar(Char::uppercaseChar) }