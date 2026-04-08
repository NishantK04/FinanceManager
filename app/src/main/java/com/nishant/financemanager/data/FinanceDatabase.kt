package com.nishant.financemanager.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Transaction::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class FinanceDatabase : RoomDatabase() {

    abstract fun dao(): TransactionDao
}