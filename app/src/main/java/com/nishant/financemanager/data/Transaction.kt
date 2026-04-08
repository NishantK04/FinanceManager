package com.nishant.financemanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "transactions")
data class Transaction(

    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),

    val title: String,
    val amount: Double,
    val type: TransactionType,
    val category: String,
    val date: String,
    val note: String
)

enum class TransactionType {
    INCOME,
    EXPENSE
}