package com.example.expensetracker.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.expensetracker.model.TransactionModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert (transactionModel: TransactionModel)

    @Update
    suspend fun update (transactionModel: TransactionModel)

    @Delete
    suspend fun delete (transactionModel: TransactionModel)

    @Query("SELECT * FROM transactions_table")
    fun getAllTransactions(): Flow<List<TransactionModel>>

    @Query("SELECT * FROM transactions_table WHERE id=:id")
    fun getTransactionById(id:Int): Flow<TransactionModel>

    @Query("SELECT * FROM transactions_table WHERE category==:category ORDER BY date ASC")
    fun getAllTransactionsByCat(category:String):LiveData<List<TransactionModel>>



}