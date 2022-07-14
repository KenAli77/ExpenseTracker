package com.example.expensetracker.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.expensetracker.R
import com.example.expensetracker.databinding.FragmentSettingsBinding
import com.example.expensetracker.util.getCurrencySymbol
import com.example.expensetracker.viewmodel.TransactionsViewModel

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var viewModel: TransactionsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        collectData()
        binding.btnSave.setOnClickListener {
            if (binding.etSpendingLimit.text!!.isNotEmpty()) {
                updateData()
                Toast.makeText(activity, getString(R.string.change_saved), Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            } else Toast.makeText(activity, getString(R.string.invalid_spending_limit), Toast.LENGTH_SHORT)
                .show()
        }
        setUpSwitch()
    }

    private fun setUpSwitch() = with(binding) {
        lifecycleScope.launchWhenCreated {
            switchDarkTheme.isChecked = viewModel.readUIPreference("night_mode") == true


                switchDarkTheme.setOnCheckedChangeListener { _, nightMode ->
                    lifecycleScope.launchWhenStarted {
                        if (nightMode) {
                            viewModel.saveUIPreference("night_mode", true)
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        } else {
                            viewModel.saveUIPreference("night_mode", false)
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        }
                    }
                }
        }
    }

    private fun collectData() = with(binding) {
        lifecycleScope.launchWhenStarted {
            viewModel.selectedCurrency.collect {
                tilSpendingLimit.prefixText = getCurrencySymbol(it)
            }
        }
        lifecycleScope.launchWhenStarted {
            val limit = viewModel.readLimit("limit")
            if(limit!=null)
            etSpendingLimit.setText(limit.toString())
        }
    }

    private fun updateData() = with(binding) {
        lifecycleScope.launchWhenStarted {
            val spendingLimit = etSpendingLimit.text.toString().toInt()
            viewModel.saveLimit("limit", spendingLimit)

        }
    }

}