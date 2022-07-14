package com.example.expensetracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.expensetracker.util.TransactionCategory
import java.io.Serializable

@Entity(tableName = "transactions_table")
data class TransactionModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "amount")
    var amount: Double,
    @ColumnInfo(name = "date")
    var date: String,
    @ColumnInfo(name = "transactionType")
    var transactionType: String,
    @ColumnInfo(name = "category")
    var category:TransactionCategory,
    @ColumnInfo(name = "note")
    var note: String?,
    ) :Serializable