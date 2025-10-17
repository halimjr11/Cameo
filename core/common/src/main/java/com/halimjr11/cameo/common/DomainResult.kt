package com.halimjr11.cameo.common

import com.halimjr11.cameo.common.Constants.ERROR_CODE_500

sealed class DomainResult<out T> {
    data class Success<out T>(val data: T) : DomainResult<T>()
    data class Error(val message: String, val code: Int = ERROR_CODE_500) : DomainResult<Nothing>()
}