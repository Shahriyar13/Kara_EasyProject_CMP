package app.duss.easyproject.presentation.utils

import io.ktor.util.date.GMTDate

fun Long.toFormattedDate(separator: String? = "."): String {
    val date = GMTDate(this)
    return "${
        date.dayOfMonth.toString().padStart(2, '0')
    }$separator${
        date.month.ordinal.plus(1).toString().padStart(2, '0')
    }$separator${
        date.year
    }"
}

fun Long.toFormattedTime(separator: String? = ":"): String {
    val date = GMTDate(this)
    return "${
        date.hours.toString().padStart(2, '0')
    }$separator${
        date.minutes.toString().padStart(2, '0')
    }$separator${
        date.seconds.toString().padStart(2, '0')
    }"
}

fun Long.toFormattedDateTime(
    separator: String? = " ",
    dateSeparator: String? = ".",
    timeSeparator: String? = ":",
): String {
    return "${
        toFormattedDate(dateSeparator)
    }$separator${
        toFormattedTime(timeSeparator)
    }"
}
