package com.kproject.chatgpt.presentation.extensions

import android.net.Uri
import android.text.format.DateFormat
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

fun Date.getFormattedDate(showOnlyDate: Boolean = false): String {
    val localizedDateFormat = DateFormat.getBestDateTimePattern(Locale.getDefault(), "dd/MM/yyyy")
    val currentLocalDay = SimpleDateFormat(localizedDateFormat, Locale.getDefault())
        .format(Calendar.getInstance().time)
    val dateDay = SimpleDateFormat(localizedDateFormat, Locale.getDefault()).format(this)
    val dateFormat = if (currentLocalDay == dateDay) {
        "HH:mm"
    } else {
        if (showOnlyDate) localizedDateFormat else "HH:mm ($localizedDateFormat)"
    }
    return SimpleDateFormat(dateFormat, Locale.getDefault()).format(this)
}

/**
 * Useful for converting data class to Json and being able to transfer data with Navigation.
 */
fun <T> String.fromJson(type: Class<T>): T {
    return Gson().fromJson(this, type)
}

fun <T> T.toJson(): String {
    return Uri.encode(Gson().toJson(this))
}