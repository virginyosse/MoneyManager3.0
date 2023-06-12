package org.d3if3117.assesment2.tampil_info

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import org.d3if3117.assesment2.R
import org.d3if3117.assesment2.network.UpdateWorker
import java.util.concurrent.TimeUnit

class TampilInfoViewModel: ViewModel() {
    private val data = MutableLiveData<List<Finance>>()
    init {
        data.value = initData()
    }
    private fun initData(): List<Finance> {
        return listOf(
            Finance("50% Needs", "Housing, Health Care", R.drawable.housing),
            Finance("30% Wants", "Personal Care, Entertainment", R.drawable.wants),
            Finance("20% Savings", "Emergencies, Savings", R.drawable.savings),
        )
    }
    fun getData(): LiveData<List<Finance>> = data

    fun scheduleUpdater(app: Application) {
        val request = OneTimeWorkRequestBuilder<UpdateWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(app).enqueueUniqueWork(
            UpdateWorker.WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
}