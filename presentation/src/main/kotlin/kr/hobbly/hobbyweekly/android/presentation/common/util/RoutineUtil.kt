package kr.hobbly.hobbyweekly.android.presentation.common.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.Routine
import kr.hobbly.hobbyweekly.android.presentation.common.alarm.InstantRoutineReceiver
import kr.hobbly.hobbyweekly.android.presentation.common.alarm.RepeatRoutineReceiver

fun Context.registerRepeatRoutineList(
    routineList: List<Routine>
) {
    val alarmManager = getSystemService(AlarmManager::class.java) ?: return
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    routineList.forEach { routine ->
        if (!routine.isAlarmEnabled) return@forEach
        val alarmTime = routine.alarmTime ?: return@forEach

        routine.smallRoutineList.forEach { smallRoutine ->
            val date = now.date
                .plus(smallRoutine.dayOfWeek - now.date.dayOfWeek.ordinal, DateTimeUnit.DAY)
                .plus(1, DateTimeUnit.WEEK)
            val time = LocalDateTime(date, alarmTime)
            val intent = makeRepeatRoutineToIntent(routine)

            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                time.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds(),
                AlarmManager.INTERVAL_DAY * 7,
                intent
            )
        }
    }
}

fun Context.registerInstantRoutineList(
    routineList: List<Routine>
) {
    val alarmManager = getSystemService(AlarmManager::class.java) ?: return
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    routineList.forEach { routine ->
        if (!routine.isAlarmEnabled) return@forEach
        val alarmTime = routine.alarmTime ?: return@forEach

        routine.smallRoutineList.forEach { smallRoutine ->
            val date = now.date
                .plus(smallRoutine.dayOfWeek - now.date.dayOfWeek.ordinal, DateTimeUnit.DAY)
            val time = LocalDateTime(date, alarmTime)
            if (time > now) {
                val intent = makeInstantRoutineToIntent(routine)

                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    time.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds(),
                    intent
                )
            }
        }
    }
}

fun Context.registerRepeatNotifyList(
    dayOfWeekList: List<Int>
) {
    val alarmManager = getSystemService(AlarmManager::class.java) ?: return
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    dayOfWeekList.forEach { dayOfWeek ->
        val date = now.date
            .plus(dayOfWeek - now.date.dayOfWeek.ordinal, DateTimeUnit.DAY)
            .plus(1, DateTimeUnit.WEEK)
        val firstTime = LocalDateTime(date, LocalTime(9, 0))
        val secondTime = LocalDateTime(date, LocalTime(21, 0))
        if (secondTime > now) {
            val intentList = makeRepeatNotifyToIntent(dayOfWeek)
            intentList.getOrNull(0)?.let { intent ->
                if (firstTime > now) {
                    alarmManager.setInexactRepeating(
                        AlarmManager.RTC_WAKEUP,
                        firstTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds(),
                        AlarmManager.INTERVAL_DAY * 7,
                        intent
                    )
                }
            }
            intentList.getOrNull(1)?.let { intent ->
                alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    secondTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds(),
                    AlarmManager.INTERVAL_DAY * 7,
                    intent
                )
            }
        }
    }
}

fun Context.registerInstantNotifyList(
    dayOfWeekList: List<Int>
) {
    val alarmManager = getSystemService(AlarmManager::class.java) ?: return
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    dayOfWeekList.forEach { dayOfWeek ->
        val date = now.date
            .plus(dayOfWeek - now.date.dayOfWeek.ordinal, DateTimeUnit.DAY)
        val firstTime = LocalDateTime(date, LocalTime(9, 0))
        val secondTime = LocalDateTime(date, LocalTime(21, 0))
        if (secondTime > now) {
            val intentList = makeInstantNotifyToIntent(dayOfWeek)
            intentList.getOrNull(0)?.let { intent ->
                if (firstTime > now) {
                    alarmManager.set(
                        AlarmManager.RTC_WAKEUP,
                        firstTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds(),
                        intent
                    )
                }
            }
            intentList.getOrNull(1)?.let { intent ->
                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    secondTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds(),
                    intent
                )
            }
        }
    }
}

fun Context.unregisterInstantRoutine(
    routine: Routine
) {
    val alarmManager = getSystemService(AlarmManager::class.java) ?: return
    val intent = makeInstantRoutineToIntent(routine)

    alarmManager.cancel(intent)
}

fun Context.unregisterRepeatRoutine(
    routine: Routine
) {
    val alarmManager = getSystemService(AlarmManager::class.java) ?: return
    val intent = makeRepeatRoutineToIntent(routine)

    alarmManager.cancel(intent)
}

fun Context.unregisterInstantNotify(
    dayOfWeek: Int
) {
    val alarmManager = getSystemService(AlarmManager::class.java) ?: return

    makeInstantNotifyToIntent(dayOfWeek).forEach { intent ->
        alarmManager.cancel(intent)
    }
}

fun Context.unregisterRepeatNotify(
    dayOfWeek: Int
) {
    val alarmManager = getSystemService(AlarmManager::class.java) ?: return

    makeRepeatNotifyToIntent(dayOfWeek).forEach { intent ->
        alarmManager.cancel(intent)
    }
}

fun Context.unregisterAlarmAll(
    routineList: List<Routine>
) {
    val alarmManager = getSystemService(AlarmManager::class.java) ?: return

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        alarmManager.cancelAll()
    } else {
        routineList.forEach { routine ->
            alarmManager.cancel(makeRepeatRoutineToIntent(routine))
            alarmManager.cancel(makeInstantRoutineToIntent(routine))
        }
        (0..6).forEach { dayOfWeek ->
            (makeRepeatNotifyToIntent(dayOfWeek) + makeInstantNotifyToIntent(dayOfWeek)).forEach { intent ->
                alarmManager.cancel(intent)
            }
        }
    }
}

private fun Context.makeInstantRoutineToIntent(
    routine: Routine
): PendingIntent {
    return Intent(this, InstantRoutineReceiver::class.java).apply {
        putExtra(InstantRoutineReceiver.NOTIFICATION_ID, routine.id.toInt())
        putExtra(InstantRoutineReceiver.NOTIFICATION_TITLE, "하비위클리")
        putExtra(InstantRoutineReceiver.NOTIFICATION_CONTENT, routine.title)
    }.let { intent ->
        PendingIntent.getBroadcast(
            this,
            routine.id.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }
}

private fun Context.makeRepeatRoutineToIntent(
    routine: Routine
): PendingIntent {
    return Intent(this, RepeatRoutineReceiver::class.java).apply {
        putExtra(RepeatRoutineReceiver.NOTIFICATION_ID, routine.id.toInt())
        putExtra(RepeatRoutineReceiver.NOTIFICATION_TITLE, "하비위클리")
        putExtra(RepeatRoutineReceiver.NOTIFICATION_CONTENT, routine.title)
    }.let { intent ->
        PendingIntent.getBroadcast(
            this,
            routine.id.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }
}

private fun Context.makeInstantNotifyToIntent(
    dayOfWeek: Int
): List<PendingIntent> {
    val first = Intent(this, InstantRoutineReceiver::class.java).apply {
        putExtra(InstantRoutineReceiver.NOTIFICATION_ID, dayOfWeek)
        putExtra(InstantRoutineReceiver.NOTIFICATION_TITLE, "하비위클리")
        putExtra(InstantRoutineReceiver.NOTIFICATION_CONTENT, "오늘은 루틴을 진행하는 날입니다.")
    }.let { intent ->
        PendingIntent.getBroadcast(
            this,
            dayOfWeek,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }
    val second = Intent(this, InstantRoutineReceiver::class.java).apply {
        putExtra(InstantRoutineReceiver.NOTIFICATION_ID, dayOfWeek)
        putExtra(InstantRoutineReceiver.NOTIFICATION_TITLE, "하비위클리")
        putExtra(InstantRoutineReceiver.NOTIFICATION_CONTENT, "오늘 루틴은 진행하셨나요? 하셨다면 인증게시글을 작성해보세요!")
    }.let { intent ->
        PendingIntent.getBroadcast(
            this,
            dayOfWeek,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }
    return listOf(first, second)
}

private fun Context.makeRepeatNotifyToIntent(
    dayOfWeek: Int
): List<PendingIntent> {
    val first = Intent(this, RepeatRoutineReceiver::class.java).apply {
        putExtra(RepeatRoutineReceiver.NOTIFICATION_ID, dayOfWeek)
        putExtra(RepeatRoutineReceiver.NOTIFICATION_TITLE, "하비위클리")
        putExtra(RepeatRoutineReceiver.NOTIFICATION_CONTENT, "오늘은 루틴을 진행하는 날입니다.")
    }.let { intent ->
        PendingIntent.getBroadcast(
            this,
            dayOfWeek,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }
    val second = Intent(this, RepeatRoutineReceiver::class.java).apply {
        putExtra(RepeatRoutineReceiver.NOTIFICATION_ID, dayOfWeek)
        putExtra(RepeatRoutineReceiver.NOTIFICATION_TITLE, "하비위클리")
        putExtra(RepeatRoutineReceiver.NOTIFICATION_CONTENT, "오늘 루틴은 진행하셨나요? 하셨다면 인증게시글을 작성해보세요!")
    }.let { intent ->
        PendingIntent.getBroadcast(
            this,
            dayOfWeek,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }
    return listOf(first, second)
}
