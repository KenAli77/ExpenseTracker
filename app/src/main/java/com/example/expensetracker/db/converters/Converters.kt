package com.example.expensetracker.db.converters

import androidx.room.TypeConverter
import com.example.expensetracker.util.TransactionCategory

class Converters {

    @TypeConverter
    fun fromTransactionCategory(category: TransactionCategory):String {
        return "${category.icon},${category.description}"
    }

    @TypeConverter
    fun toTransactionCategory(value:String): TransactionCategory {
        val list = value.split(",")
        val description = list[0].toInt()
        val icon = list[1].toInt()
       return TransactionCategory(icon,description)
    }


}