package com.example.notes.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.getCurrentTimestamp(): String {
    val format = SimpleDateFormat("MMM yyyy", Locale.getDefault())
    return format.format(this)
}