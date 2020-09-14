package com.khs.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import timber.log.Timber

class CompressingWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        return try {
            for (i in 0 .. 500) {
                Timber.d("Compressing $i")
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}