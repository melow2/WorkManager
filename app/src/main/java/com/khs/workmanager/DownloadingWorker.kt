package com.khs.workmanager

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class DownloadingWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        return try {
            for (i in 0 .. 3000) {
                Timber.d("Downloading $i")
            }
            val time = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = time.format(Date())
            Timber.d("Completed $currentDate")
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}