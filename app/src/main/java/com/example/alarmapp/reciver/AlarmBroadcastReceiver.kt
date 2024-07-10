package com.example.alarmapp.reciver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Data
import com.example.alarmapp.manager.ScheduleAlarmManager
import com.example.alarmapp.manager.WorkRequestManager
import com.example.alarmapp.workManager.worker.ALARM_TAG
import com.example.alarmapp.workManager.worker.AlarmWorker
import com.example.alarmapp.workManager.worker.RESCHEDULE_ALARM_TAG
import com.example.alarmapp.workManager.worker.RescheduleAlarmWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject


@AndroidEntryPoint
class AlarmBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var scheduleAlarmManager: ScheduleAlarmManager

    @Inject
    lateinit var workRequestManager: WorkRequestManager

    private val broadcastReceiverScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override fun onReceive(context: Context?, intent: Intent?) {
        val pendingResult = goAsync()
        broadcastReceiverScope.launch {
            try {
                intent?.let {
                    when (it.action) {
                        Intent.ACTION_BOOT_COMPLETED -> {
                            workRequestManager.enqueueWorker<RescheduleAlarmWorker>(
                                RESCHEDULE_ALARM_TAG
                            )
                        }

                        ACTION_DISMISS -> {
                            workRequestManager.cancelWorker(ALARM_TAG)
                        }

                        ACTION_SNOOZE -> {
                            scheduleAlarmManager.snooze()
                            workRequestManager.cancelWorker(ALARM_TAG)
                        }

                        else -> {
                            handleAlarmIntent(it)
                        }
                    }
                }
            } finally {
                pendingResult.finish()
                broadcastReceiverScope.cancel()
            }
        }
    }

    private fun handleAlarmIntent(intent: Intent) {
        val isRecurring = intent.getBooleanExtra(IS_RECURRING, false)
        val shouldStartWorker = !isRecurring || alarmIsToday(intent)
        if (shouldStartWorker) {
            val inputData = Data.Builder()
                .putString(TITLE, intent.getStringExtra(TITLE))
                .putString(HOUR, intent.getStringExtra(HOUR))
                .putString(MINUTE, intent.getStringExtra(MINUTE))
                .build()
            workRequestManager.enqueueWorker<AlarmWorker>(ALARM_TAG, inputData)
        }
    }

    private fun alarmIsToday(intent: Intent): Boolean {
        val daysSelected =
            intent.extras?.getSerializable(DAYS_SELECTED) as? HashMap<String, Boolean>
        val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        return daysSelected?.get(getDayOfWeek(today)) ?: false
    }

    private fun getDayOfWeek(day: Int): String {
        return when (day) {
            Calendar.SATURDAY -> "Sat"
            Calendar.SUNDAY -> "Sun"
            Calendar.MONDAY -> "Mon"
            Calendar.TUESDAY -> "Tue"
            Calendar.WEDNESDAY -> "Wed"
            Calendar.THURSDAY -> "Thu"
            Calendar.FRIDAY -> "Fri"
            else -> ""
        }
    }
}

const val IS_RECURRING = "IS_RECURRING"
const val DAYS_SELECTED = "DAYS_SELECTED"
const val TITLE = "TITLE"
const val HOUR = "HOUR"
const val MINUTE = "MINUTE"
const val ACTION_DISMISS = "ACTION_DISMISS"
const val ACTION_SNOOZE = "ACTION_SNOOZE"