package com.halimjr11.cameo.common

fun String.capitalizeWords(): String =
    split(" ").joinToString(" ") { it.replaceFirstChar(Char::uppercaseChar) }