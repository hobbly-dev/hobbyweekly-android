package kr.hobbly.hobbyweekly.android.common.util

fun Char?.orEmpty(): Char {
    return this ?: Char.MIN_VALUE
}

fun String.takeIfNotEmpty(): String? {
    return takeIf { it.isNotEmpty() }
}
