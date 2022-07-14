package com.example.expensetracker.util

import android.app.Application
import android.content.Context
import com.example.expensetracker.R
import javax.inject.Inject

class Constants ( context: Context) {

    val TRANSACTION_TYPE = listOf(
        context.getString(R.string.income),
        context.getString(R.string.expenses)
    )

    val TRANSACTION_CATEGORY = arrayListOf(
        context.getString(TransactionCategory.Bills.description),
        context.getString(TransactionCategory.Food.description),
        context.getString(TransactionCategory.Education.description),
        context.getString(TransactionCategory.Entertainment.description),
        context.getString(TransactionCategory.Housing.description),
        context.getString(TransactionCategory.Health.description),
        context.getString(TransactionCategory.Travel.description),
        context.getString(TransactionCategory.Transportation.description),
        context.getString(TransactionCategory.Shopping.description),
        context.getString(TransactionCategory.Salary.description),
        context.getString(TransactionCategory.Investments.description),
        context.getString(TransactionCategory.Other.description)
    )


}