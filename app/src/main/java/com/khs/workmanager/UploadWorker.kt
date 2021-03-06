package com.khs.workmanager

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.khs.workmanager.MainActivity.Companion.KEY_COUNT_VALUE
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class UploadWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {
    companion object{
        const val KEY_WORKER = "key_worker"
    }
    override fun doWork(): Result {
        return try {
            val count = inputData.getInt(KEY_COUNT_VALUE,0)
            for (i in 0 until count) {
                Timber.d("Uploading $i")
            }
            val time = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentData = time.format(Date())
            val outputData = Data.Builder().putString(KEY_WORKER,currentData).build()
            Result.success(outputData)
        } catch (e: Exception) {
            Result.failure()
        }
    }
}