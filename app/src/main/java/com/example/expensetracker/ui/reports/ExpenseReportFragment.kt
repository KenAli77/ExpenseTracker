package com.example.expensetracker.ui.reports

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.expensetracker.R
import com.example.expensetracker.databinding.FragmentExpenseReportBinding
import com.example.expensetracker.ui.MainActivity
import com.example.expensetracker.util.TransactionCategory
import com.example.expensetracker.viewmodel.TransactionsViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate

class ExpenseReportFragment :
    Fragment(R.layout.fragment_expense_report) {

    private lateinit var binding: FragmentExpenseReportBinding
    private lateinit var viewModel: TransactionsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentExpenseReportBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        setUpPieChart()
        observeChartData()
    }

    private fun setUpPieChart() = with(binding.expenseChart) {
        isDrawHoleEnabled = true
        setUsePercentValues(true)
        setEntryLabelTextSize(12f)
        setEntryLabelColor(R.color.black)
        centerText = getString(R.string.expenses_by_category)
        setCenterTextSize(20f)
        description.isEnabled = false
        legend.apply {
            verticalAlignment = Legend.LegendVerticalAlignment.TOP
            horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            orientation = Legend.LegendOrientation.VERTICAL
            setDrawInside(false)
            isEnabled = false
        }
    }

    private fun observeChartData() =
        viewModel.transaction.observe(viewLifecycleOwner) { transactions ->

            val entryValues = ArrayList<PieEntry>()
            val chart = binding.expenseChart
            val expenses = transactions.filter { it.transactionType == "Expense" }
            val totalExpense = expenses.sumOf { it.amount }


            for (category in TransactionCategory.TRANSACTION_CATEGORIES) {


                val categoryList =
                    expenses.filter { it.category.description == category.description }
                val totalCategory = categoryList.sumOf { it.amount }
                val percentageValue = (totalCategory / totalExpense).toFloat()
                if (totalCategory > 0) {
                    entryValues.add(PieEntry(percentageValue, getString(category.description)))
                }
            }
            val colors = ArrayList<Int>()
            for (color in ColorTemplate.MATERIAL_COLORS) {
                colors.add(color)
            }
            for (color in ColorTemplate.VORDIPLOM_COLORS) {
                colors.add(color)
            }

            val dataSet = PieDataSet(entryValues, getString(R.string.expenses))
            dataSet.colors = colors

            val pieData = PieData(dataSet)

            val tf = Typeface.DEFAULT
            pieData.apply {
                setDrawValues(true)
                setValueFormatter(PercentFormatter(chart))
                setValueTextSize(12f)
                setValueTextColor(R.color.black)
                setValueTypeface(tf)
            }
            chart.data = pieData
            chart.invalidate()

            chart.animateY(1400, Easing.EaseInOutQuad)
        }
}