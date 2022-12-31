package com.kproject.chatgpt.presentation.extensions

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