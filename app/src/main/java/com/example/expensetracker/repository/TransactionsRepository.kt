package com.example.expensetracker.repository

import androidx.lifecycle.LiveData
import com.example.expensetracker.db.TransactionsDatabase
import com.example.expensetracker.model.TransactionModel
import kotlinx.coroutines.flow.Flow

class TransactionsRepository(private val db: TransactionsDatabase) {


    suspend fun insert(transaction: TransactionModel) = db.transactionsDao().insert(transaction)

    suspend fun update(transaction: TransactionModel) = db.transactionsDao().update(transaction)

    suspend fun delete(transaction: TransactionModel) = db.transactionsDao().delete(transaction)

    fun getAllTransactions(): Flow<List<TransactionModel>> =
        db.transactionsDao().getAllTransactions()

    fun getTransactionById(id: Int): Flow<TransactionModel> =
        db.transactionsDao().getTransactionById(id)


}