package com.example.alarmapp.workManager.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.example.alarmapp.db.AlarmRepository
import com.example.alarmapp.manager.WorkRequestManager
import com.example.alarmapp.workManager.worker.AlarmWorker
import com.example.alarmapp.util.helper.AlarmNotificationHelper
import com.example.alarmapp.util.helper.MediaPlayerHelper
import javax.inject.Inject

class AlarmWorkerFactory @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val alarmNotificationHelper: AlarmNotificationHelper,
    private val mediaPlayerHelper: MediaPlayerHelper,
    private val workRequestManager: WorkRequestManager,
) : ChildWorkerFactory {

    override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
        return AlarmWorker(alarmRepository, alarmNotificationHelper, mediaPlayerHelper, workRequestManager, appContext, params)
    }
}
