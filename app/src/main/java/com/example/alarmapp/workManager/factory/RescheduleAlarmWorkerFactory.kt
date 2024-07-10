package com.example.alarmapp.workManager.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.example.alarmapp.db.AlarmRepository
import com.example.alarmapp.manager.ScheduleAlarmManager
import com.example.alarmapp.manager.WorkRequestManager
import com.example.alarmapp.workManager.worker.RescheduleAlarmWorker
import javax.inject.Inject

class RescheduleAlarmWorkerFactory @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val scheduleAlarmManager: ScheduleAlarmManager,
    private val workRequestManager: WorkRequestManager,
) : ChildWorkerFactory {

    override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
        return RescheduleAlarmWorker(alarmRepository, scheduleAlarmManager, workRequestManager, appContext, params)
    }
}
