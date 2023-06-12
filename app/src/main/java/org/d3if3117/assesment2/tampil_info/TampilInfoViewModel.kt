package org.d3if3117.assesment2.tampil_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.d3if3117.assesment2.R

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
}