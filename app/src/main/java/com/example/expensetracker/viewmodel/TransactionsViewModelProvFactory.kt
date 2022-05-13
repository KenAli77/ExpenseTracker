package com.example.expensetracker.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.TransactionApp
import com.example.expensetracker.repository.TransactionsRepository

class TransactionsViewModelProvFactory(val repository: TransactionsRepository, val app: Application):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TransactionsViewModel(repository,app) as T
    }
}