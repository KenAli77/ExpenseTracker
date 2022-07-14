package com.example.expensetracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.expensetracker.db.converters.Converters
import com.example.expensetracker.model.TransactionModel

@Database(entities = [TransactionModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class TransactionsDatabase : RoomDatabase() {

    abstract fun transactionsDao(): TransactionsDao

    companion object {
        @Volatile
        private var INSTANCE: TransactionsDatabase? = null

        fun getInstance(context: Context): TransactionsDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TransactionsDatabase::class.java,
                        "transactions_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance

                }
                return instance

            }

        }
    }

}