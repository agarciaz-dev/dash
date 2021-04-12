package com.eelseth.core.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun String.toDateTimestamp(dateFormat: String): Long? {
    return try {
        val date = SimpleDateFormat(dateFormat, Locale.getDefault()).parse(this)
        date?.time
    } catch (e: ParseException) {
        null
    }
}