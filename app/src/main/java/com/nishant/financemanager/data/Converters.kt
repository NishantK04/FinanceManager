package com.nishant.financemanager.data

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromType(value: TransactionType): String {
        return value.name
    }

    @TypeConverter
    fun toType(value: String): TransactionType {
        return TransactionType.valueOf(value)
    }
}