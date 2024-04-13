package kr.hobbly.hobbyweekly.android.presentation.common.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
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
        val year = now.date.year
        val month = now.date.month.number
        val alarmTime = routine.alarmTime ?: return@forEach

        routine.smallRoutineList.forEach { smallRoutine ->
            val day = now.date.dayOfMonth + (smallRoutine.dayOfWeek - now.date.dayOfWeek.ordinal)
            val date = LocalDate(year, month, day).plus(1, DateTimeUnit.WEEK)
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
        val year = now.date.year
        val month = now.date.month.number
        val alarmTime = routine.alarmTime ?: return@forEach

        routine.smallRoutineList.forEach { smallRoutine ->
            val day = now.date.dayOfMonth + (smallRoutine.dayOfWeek - now.date.dayOfWeek.ordinal)
            val date = LocalDate(year, month, day)
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
