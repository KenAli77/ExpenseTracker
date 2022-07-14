package com.example.expensetracker.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.expensetracker.R


data class TransactionCategory(
    @StringRes
    val description: Int,
    @DrawableRes
    val icon: Int
) {

    companion object {

        val Bills = TransactionCategory(
            description = R.string.bills,
            icon = R.drawable.ic_utility
        )

        val Food = TransactionCategory(
            description = R.string.food,
            icon = R.drawable.ic_food
        )

        val Education = TransactionCategory(
            description = R.string.education,
            icon = R.drawable.ic_education
        )

        val Entertainment = TransactionCategory(
            description = R.string.entertainment,
            icon = R.drawable.ic_entertainment
        )

        val Housing = TransactionCategory(
            description = R.string.housing,
            icon = R.drawable.ic_entertainment
        )

        val Health = TransactionCategory(
            description = R.string.health,
            icon = R.drawable.ic_health
        )

        val Travel = TransactionCategory(
            description = R.string.travel,
            icon = R.drawable.ic_travel
        )

        val Transportation = TransactionCategory(
            description = R.string.transportation,
            icon = R.drawable.ic_transportation
        )

        val Shopping = TransactionCategory(
            description = R.string.shopping,
            icon = R.drawable.ic_shopping
        )

        val Salary = TransactionCategory(
            description = R.string.salary,
            icon = R.drawable.ic_salary
        )

        val Investments = TransactionCategory(
            description = R.string.investments,
            icon = R.drawable.ic_investments
        )

        val Other = TransactionCategory(
            description = R.string.other,
            icon = R.drawable.ic_other
        )


        val TRANSACTION_CATEGORIES = arrayListOf(
            Bills,
            Food,
            Education,
            Entertainment,
            Housing,
            Travel,
            Transportation,
            Shopping,
            Salary,
            Investments,
            Other
        )
    }


}


