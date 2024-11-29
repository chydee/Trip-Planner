package com.chidi.voyatektripplanner.ui.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.format(
    pattern: String = "dd MMM yyyy"
): String = SimpleDateFormat(
    pattern, Locale.getDefault()
).format(this)