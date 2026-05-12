package com.nishant.financemanager.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nishant.financemanager.data.Transaction
import com.nishant.financemanager.data.TransactionDao
import com.nishant.financemanager.data.TransactionType
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FinanceViewModel(
    private val dao: TransactionDao
) : ViewModel() {

    val transactions =
        dao.getAll()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                emptyList()
            )

    val monitoringEnabled = MutableStateFlow(false)

    fun toggleMonitoring(context: Context, enable: Boolean) {

        monitoringEnabled.value = enable

        if (enable) {

            if (!isAccessibilityServiceEnabled(context)) {
                openAccessibilitySettings(context)
            }

        } else {

            android.widget.Toast.makeText(
                context,
                "Disable Smart Monitoring from Accessibility Settings",
                android.widget.Toast.LENGTH_LONG
            ).show()
        }
    }
    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            dao.insert(transaction)
        }
    }
    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            dao.delete(transaction)
        }
    }

    val totalIncome =
        transactions.map {
            it.filter { t -> t.type == TransactionType.INCOME }
                .sumOf { t -> t.amount }
        }

    val totalExpense =
        transactions.map {
            it.filter { t -> t.type == TransactionType.EXPENSE }
                .sumOf { t -> t.amount }
        }

    val balance =
        combine(totalIncome, totalExpense) { income, expense ->
            income - expense
        }

    val categoryTotals =
        transactions.map { list ->
            list.filter { it.type == TransactionType.EXPENSE }
                .groupBy { it.category }
                .mapValues { entry ->
                    entry.value.sumOf { it.amount }
                }
        }
}

private fun isAccessibilityServiceEnabled(context: Context): Boolean {
    val am = context.getSystemService(Context.ACCESSIBILITY_SERVICE)
            as android.view.accessibility.AccessibilityManager

    val enabledServices = am.getEnabledAccessibilityServiceList(
        android.accessibilityservice.AccessibilityServiceInfo.FEEDBACK_ALL_MASK
    )

    return enabledServices.any {
        it.resolveInfo.serviceInfo.name.contains("MonitorAccessibilityService")
    }
}

private fun openAccessibilitySettings(context: Context) {
    val intent = android.content.Intent(
        android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS
    )
    intent.flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
}