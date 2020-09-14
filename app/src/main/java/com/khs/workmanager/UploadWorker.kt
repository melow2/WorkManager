package com.khs.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import timber.log.Timber

class UploadWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {
    override fun doWork(): Result {
        return try {
            for (i in 0..60000) {
                Timber.d("Uploading $i")
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}