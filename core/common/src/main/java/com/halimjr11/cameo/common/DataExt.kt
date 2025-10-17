package com.halimjr11.cameo.common

import android.net.http.HttpException
import java.io.IOException

fun Boolean?.orFalse(): Boolean = this ?: false

fun Int?.orZero(): Int = this ?: 0

fun Double?.orDoubleZero(): Double = this ?: 0.0