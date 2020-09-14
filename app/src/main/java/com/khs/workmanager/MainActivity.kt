package com.khs.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(Timber.DebugTree())
        btn_start.setOnClickListener {
            setOneTimeWorkRequest()
        }
    }
    private fun setOneTimeWorkRequest(){
        val workManager = WorkManager.getInstance(applicationContext)
        val constraints = Constraints.Builder().setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
                .setConstraints(constraints)
                .build()
        workManager.enqueue(uploadRequest)
        workManager.getWorkInfoByIdLiveData(uploadRequest.id)
                .observe(this, {
                    tv_test.text = it.state.name
                })
    }
}