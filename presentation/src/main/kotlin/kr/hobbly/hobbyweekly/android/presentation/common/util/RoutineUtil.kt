package kr.hobbly.hobbyweekly.android.presentation.common.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kr.hobbly.hobbyweekly.android.domain.model.feature.routine.Routine
import kr.hobbly.hobbyweekly.android.presentation.common.alarm.AlarmReceiver

fun Context.registerRoutineList(
    routineList: List<Routine>
) {
    val alarmManager = getSystemService(AlarmManager::class.java) ?: return
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    routineList.forEach { routine ->
        if (!routine.isEnabled) return@forEach
        val year = now.date.year
        val month = now.date.month.number
        val alarmTime = routine.alarmTime ?: return@forEach

        routine.smallRoutine.forEach { smallRoutine ->
            val day = now.date.dayOfMonth + (smallRoutine.dayOfWeek - now.date.dayOfWeek.ordinal)
            val time = LocalDateTime(LocalDate(year, month, day), alarmTime)
            val intent = makeRoutineToIntent(routine)

            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                time.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds(),
                AlarmManager.INTERVAL_DAY * 7,
                intent
            )
        }
    }
}

fun Context.unregisterRoutine(
    routine: Routine
) {
    val alarmManager = getSystemService(AlarmManager::class.java) ?: return
    val intent = makeRoutineToIntent(routine)

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
            val intent = makeRoutineToIntent(routine)

            alarmManager.cancel(intent)
        }
    }
}

private fun Context.makeRoutineToIntent(
    routine: Routine
): PendingIntent {
    return Intent(this, AlarmReceiver::class.java).apply {
        putExtra(AlarmReceiver.NOTIFICATION_ID, routine.id.toInt())
        putExtra(AlarmReceiver.NOTIFICATION_TITLE, "하비위클리")
        putExtra(AlarmReceiver.NOTIFICATION_CONTENT, routine.title)
    }.let { intent ->
        PendingIntent.getBroadcast(
            this,
            routine.id.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }
}
