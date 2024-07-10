package com.example.alarmapp.workManager.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.example.alarmapp.db.AlarmRepository
import com.example.alarmapp.manager.WorkRequestManager
import com.example.alarmapp.workManager.worker.AlarmCheckerWorker
import com.example.alarmapp.util.helper.AlarmNotificationHelper
import javax.inject.Inject

class AlarmCheckerWorkerFactory @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val alarmNotificationHelper: AlarmNotificationHelper,
    private val workRequestManager: WorkRequestManager,
) : ChildWorkerFactory {

    override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
        return AlarmCheckerWorker(alarmRepository, alarmNotificationHelper, workRequestManager, appContext, params)
    }
}
