package com.kproject.chatgpt.presentation.extensions

import android.net.Uri
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

fun Date.getFormattedDate(): String {
    val currentLocalDay = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        .format(Calendar.getInstance().time)
    val currentTimestampDay = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        .format(this)
    val dateFormat =
            if (currentLocalDay == currentTimestampDay) "HH:mm" else "HH:mm, dd MMM yyyy"
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