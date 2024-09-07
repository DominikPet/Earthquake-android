package com.pero.earthquakeapp.api

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class InfoWorker(private val context: Context, workerParameters: WorkerParameters)
    : Worker(context, workerParameters) {
    override fun doWork(): Result {
        InfoFetcher(context).fetchItems(10)
        return Result.success()
    }
}