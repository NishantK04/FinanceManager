package com.nishant.financemanager.viewmodel

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
}