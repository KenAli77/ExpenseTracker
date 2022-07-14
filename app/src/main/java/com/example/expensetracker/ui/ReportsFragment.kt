package com.example.expensetracker.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.expensetracker.R
import com.example.expensetracker.adapter.ViewPagerAdapter
import com.example.expensetracker.databinding.FragmentReportsBinding
import com.example.expensetracker.ui.reports.ExpenseReportFragment
import com.example.expensetracker.ui.reports.IncomeReportFragment


class ReportsFragment : Fragment(R.layout.fragment_reports) {

    private lateinit var binding: FragmentReportsBinding
    private lateinit var adapter: ViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReportsBinding.bind(view)
        setUpTabs()
    }


    private fun setUpTabs() = with(binding) {

        adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(IncomeReportFragment(), getString(R.string.income))
        adapter.addFragment(ExpenseReportFragment(), getString(R.string.expenses))
        viewPager.adapter = adapter
        tabs.setupWithViewPager(binding.viewPager)


    }


}