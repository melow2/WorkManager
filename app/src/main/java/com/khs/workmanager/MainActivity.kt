package com.khs.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    companion object{
        const val KEY_COUNT_VALUE = "key_count"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(Timber.DebugTree())
        btn_start.setOnClickListener {
            // setOneTimeWorkRequest()
            setPeriodicWorkRequest()
        }
    }

    private fun setPeriodicWorkRequest() {
        val periodicWorkRequest = PeriodicWorkRequest.Builder(DownloadingWorker::class.java,16,TimeUnit.MINUTES) // minimum 15 min
                .build()
        WorkManager.getInstance(applicationContext).enqueue(periodicWorkRequest)
    }

    private fun setOneTimeWorkRequest(){

        val workManager = WorkManager.getInstance(applicationContext)
        val data:Data  = Data.Builder()
                .putInt(KEY_COUNT_VALUE,125)
                .build()
        val constraints = Constraints.Builder().setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
                .setConstraints(constraints)
                .setInputData(data)
                .build()

        val filteringRequest = OneTimeWorkRequest.Builder(FilteringWorker::class.java).build()
        val compressingRequest =  OneTimeWorkRequest.Builder(CompressingWorker::class.java).build()
        val downloadingRequest =  OneTimeWorkRequest.Builder(DownloadingWorker::class.java).build()
        val paralleWorks = mutableListOf<OneTimeWorkRequest>()

        paralleWorks.add(downloadingRequest)
        paralleWorks.add(filteringRequest)
        workManager.beginWith(paralleWorks)
                .then(compressingRequest)
                .then(uploadRequest)
                .enqueue()

        workManager.getWorkInfoByIdLiveData(uploadRequest.id)
                .observe(this, {
                    tv_test.text = it.state.name
                    if(it.state.isFinished){
                        val data = it.outputData
                        val msg = data.getString(UploadWorker.KEY_WORKER)
                        Toast.makeText(applicationContext,msg,Toast.LENGTH_SHORT).show()
                    }
                })
    }
}