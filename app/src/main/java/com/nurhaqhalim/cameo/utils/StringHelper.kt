package com.nurhaqhalim.cameo.utils

import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun Locale.getCurrentTimeStamp(): String {
    val currentTimestamp = System.currentTimeMillis()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", this)
    return dateFormat.format(Date(currentTimestamp))
}


fun String.toSpannableHyperLink(
    isLogin: Boolean, locale: String, color: Int, actionClick: () -> Unit
): SpannableString {
    val spannableString = SpannableStringBuilder(this)

    val registerText = if (locale == "in") "Login sekarang" else "Login now"
    val loginText = if (locale == "in") "Daftar sekarang" else "Register now"

    val startLogin = this.indexOf(loginText)
    val endLogin = startLogin + loginText.length

    val startRegister = this.indexOf(registerText)
    val endRegister = startRegister + registerText.length

    val clickableSpan = object : ClickableSpan() {
        override fun onClick(view: View) {
            actionClick.invoke()
        }
    }
    if (isLogin) {
        spannableString.setSpan(
            ForegroundColorSpan(color), startLogin, endLogin, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            clickableSpan, startLogin, endLogin, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    } else {
        spannableString.setSpan(
            ForegroundColorSpan(color),
            startRegister,
            endRegister,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            clickableSpan, startRegister, endRegister, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    return SpannableString.valueOf(spannableString)
}
