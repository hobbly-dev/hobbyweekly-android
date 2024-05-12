package kr.hobbly.hobbyweekly.android.presentation.common.util

import androidx.compose.runtime.Composable
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

@Composable
fun LocalDateTime.toDurationString(): String {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val duration =
        now.toInstant(TimeZone.currentSystemDefault()) - (toInstant(TimeZone.currentSystemDefault()))

    // TODO : Localization

    return when {
        duration.inWholeMinutes < 1 -> {
            "${duration.inWholeSeconds}초 전"
        }

        duration.inWholeHours < 1 -> {
            "${duration.inWholeMinutes}분 전"
        }

        duration.inWholeDays < 1 -> {
            "${duration.inWholeHours}시간 전"
        }

        duration.inWholeDays < 8 -> {
            "${duration.inWholeDays}일 전"
        }

        date.year == now.year -> {
            val format = "%02d월 %02d일"
            runCatching {
                String.format(
                    format,
                    date.month.number,
                    date.dayOfMonth
                )
            }.getOrDefault("??월 ??일")
        }

        else -> {
            val format = "%02d년 %02d월 %02d일"
            runCatching {
                String.format(
                    format,
                    date.year % 100,
                    date.year,
                    date.month.number
                )
            }.getOrDefault("??년 ??월 ??일")
        }
    }
}
