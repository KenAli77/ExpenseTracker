package com.example.expensetracker.util

import android.content.Context
import com.example.expensetracker.R

class Constants(context:Context) {

    val TRANSACTION_TYPE = listOf(
        context.getString(R.string.income),
        context.getString(R.string.expenses)
    )

    val TRANSACTION_CATEGORY = listOf(
        context.getString(R.string.bills),
        context.getString(R.string.food),
        context.getString(R.string.education),
        context.getString(R.string.entertainment),
        context.getString(R.string.housing),
        context.getString(R.string.health),
        context.getString(R.string.travel),
        context.getString(R.string.transportation),
        context.getString(R.string.shopping),
        context.getString(R.string.salary),
        context.getString(R.string.investments),
        context.getString(R.string.other)
    )

    val categories = listOf(
        "bills",
        "food",
        "education",
        "entertainment",
        "housing",
        "health",
        "travel",
        "transportation",
        "shopping",
        "salary",
        "investments",
        "other"
    )


}