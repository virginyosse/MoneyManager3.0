package org.d3if3117.assesment2.tampil_info

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3117.assesment2.R
import org.d3if3117.assesment2.network.ApiStatus
import org.d3if3117.assesment2.network.MoneyApi
import org.d3if3117.assesment2.network.MoneyApiService
import org.d3if3117.assesment2.network.UpdateWorker
import java.util.concurrent.TimeUnit

class TampilInfoViewModel: ViewModel() {
    private val data = MutableLiveData<List<Finance>>()
    private val status = MutableLiveData<ApiStatus>()
    init {
        retrieveData()

    }
//    private fun initData(): List<Finance> {
//        return listOf(
//            Finance("50% Needs", "Housing, Health Care", R.drawable.housing),
//            Finance("30% Wants", "Personal Care, Entertainment", R.drawable.wants),
//            Finance("20% Savings", "Emergencies, Savings", R.drawable.savings),
//        )
//    }
    private fun retrieveData() {
        viewModelScope.launch (Dispatchers.IO) {
            status.postValue(ApiStatus.LOADING)
            try {
                data.postValue(MoneyApi.service.getData())
                status.postValue(ApiStatus.SUCCESS)
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.postValue(ApiStatus.FAILED)
            }
        }
    }

    fun getData(): LiveData<List<Finance>> = data
    fun getStatus(): LiveData<ApiStatus> = status

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