package com.example.expensetracker.viewmodel

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.*
import com.example.expensetracker.TransactionApp
import com.example.expensetracker.model.TransactionModel
import com.example.expensetracker.repository.TransactionsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class TransactionsViewModel(
    private val repository: TransactionsRepository,
    app: Application
) : AndroidViewModel(app) {

    private val Context.limitDataStore by preferencesDataStore("spending_limit")
    private val Context.UIdataStore by preferencesDataStore("UI_preference")
    private val limitDataStore = getApplication<TransactionApp>().limitDataStore
    private val UIdataStore = getApplication<TransactionApp>().UIdataStore

    private val _period = MutableLiveData(Period.ofYears(5))
    var period = _period

    var transaction: LiveData<List<TransactionModel>> =
        getAllTransactions().asLiveData()

    private val _isWarningClosed = MutableLiveData(false)
    var isWarningClosed = _isWarningClosed

    private val _selectedCurrency = MutableStateFlow("USD")
    val selectedCurrency = _selectedCurrency

    suspend fun readUIPreference(key: String): Boolean? {
        val dataStoreKey = booleanPreferencesKey(key)
        val preferences = UIdataStore.data.first()
        return preferences[dataStoreKey]
    }

    suspend fun saveUIPreference(key: String, value: Boolean) {
        val preferenceKey = booleanPreferencesKey(key)
        UIdataStore.edit {
            it[preferenceKey] = value
        }
    }

    suspend fun saveLimit(key: String, value: Int) {
        val preferenceKey = intPreferencesKey(key)
        limitDataStore.edit {
            it[preferenceKey] = value
        }
    }

    suspend fun readLimit(key: String): Int? {
        val dataStoreKey = intPreferencesKey(key)
        val preferences = limitDataStore.data.first()
        return preferences[dataStoreKey]
    }

    init {
        transaction
    }

    fun filterAllTransactions(filterPeriod: Period? = period.value):LiveData<List<TransactionModel>> =
        if (filterPeriod != null) {
            repository.getAllTransactions().map { list ->
                list.filter { transaction ->
                    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    val formattedDate = LocalDate.parse(transaction.date, dateFormatter)
                    val currentDate = LocalDate.now()

                    formattedDate in currentDate.minus(filterPeriod)..currentDate
                }
            }.asLiveData()
        }
            else repository.getAllTransactions().asLiveData()

    fun getAllTransactions() = repository.getAllTransactions()

    fun getTransactionById(id: Int) = repository.getTransactionById(id)

    /*
    fun getAllTransactionByCat(category: String) = repository.getAllTransactionsByCat(category)

     */

    fun insertTransaction(transaction: TransactionModel) =
        viewModelScope.launch { repository.insert(transaction) }

    fun deleteTransaction(transaction: TransactionModel) =
        viewModelScope.launch { repository.delete(transaction) }

    fun updateTransaction(transaction: TransactionModel) =
        viewModelScope.launch { repository.update(transaction) }
}