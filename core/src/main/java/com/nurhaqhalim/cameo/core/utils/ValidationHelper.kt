package com.nurhaqhalim.cameo.core.utils

fun String?.validateEmail(): Boolean {
    val emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\$"
    val isValid = this?.let { emailRegex.toRegex().matches(it) }
    return isValid == true
}

fun String?.validatePassword(): Boolean {
    val isValid = this?.let { it.length >= 8 }
    return isValid == true
}

fun String?.validateRequired(): Boolean = this?.isNotEmpty() == true
