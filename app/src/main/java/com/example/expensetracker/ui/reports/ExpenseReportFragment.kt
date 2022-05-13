package com.example.expensetracker.ui.reports

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.expensetracker.R
import com.example.expensetracker.databinding.FragmentExpenseReportBinding
import com.example.expensetracker.ui.MainActivity
import com.example.expensetracker.util.Constants
import com.example.expensetracker.viewmodel.TransactionsViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate

class ExpenseReportFragment : Fragment(R.layout.fragment_expense_report) {

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

    private fun observeChartData() = viewModel.transaction.observe(viewLifecycleOwner) { transactions ->

        val entryValues = ArrayList<PieEntry>()
        val chart = binding.expenseChart
        val expenses = transactions.filter { it.transactionType == "Expense" }
        val totalExpense = expenses.sumOf { it.amount }


        for (category in Constants(requireContext()).categories) {

            val categoryName = when (category) {
                "bills" -> getString(R.string.bills)
                "food" -> getString(R.string.food)
                "education" -> getString(R.string.education)
                "entertainment" -> getString(R.string.entertainment)
                "housing" -> getString(R.string.housing)
                "health" -> getString(R.string.health)
                "travel" -> getString(R.string.travel)
                "transportation" -> getString(R.string.transportation)
                "shopping" -> getString(R.string.shopping)
                "salary" -> getString(R.string.salary)
                "investments" -> getString(R.string.investments)
                "other" -> getString(R.string.other)
                else -> getString(R.string.other)
            }

            val categoryList = expenses.filter { it.category == category }
            val totalCategory = categoryList.sumOf { it.amount }
            val percentageValue = (totalCategory / totalExpense).toFloat()
            if (totalCategory > 0) {
                entryValues.add(PieEntry(percentageValue, categoryName))
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

        chart.animateY(1400,Easing.EaseInOutQuad)
    }
}