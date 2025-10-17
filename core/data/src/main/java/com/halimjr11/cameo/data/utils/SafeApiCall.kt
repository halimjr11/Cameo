package com.halimjr11.cameo.data.utils

import com.halimjr11.cameo.common.Constants.NETWORK_ERROR
import com.halimjr11.cameo.common.Constants.UNKNOWN_ERROR
import com.halimjr11.cameo.common.DomainResult
import retrofit2.HttpException
import java.io.IOException

suspend inline fun <T> safeApiCall(
    crossinline apiCall: suspend () -> T
): DomainResult<T> {
    return try {
        val response = apiCall()
        DomainResult.Success(response)
    } catch (e: HttpException) {
        DomainResult.Error(e.message(), e.code())
    } catch (e: IOException) {
        DomainResult.Error(NETWORK_ERROR)
    } catch (e: Exception) {
        DomainResult.Error(e.localizedMessage ?: UNKNOWN_ERROR)
    }
}