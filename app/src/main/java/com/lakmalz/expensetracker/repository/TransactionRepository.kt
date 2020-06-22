package com.lakmalz.expensetracker.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.lakmalz.expensetracker.data.db.appdatabase.AppDatabase
import com.lakmalz.expensetracker.data.db.dao.AccountDataDao
import com.lakmalz.expensetracker.data.db.dao.ExpenseCatDataDao
import com.lakmalz.expensetracker.data.db.dao.IncomeCatDataDao
import com.lakmalz.expensetracker.data.db.dao.TransactionsDataDao
import com.lakmalz.expensetracker.data.db.entity.AccountsData
import com.lakmalz.expensetracker.data.db.entity.ExpenseCatData
import com.lakmalz.expensetracker.data.db.entity.IncomeCatData
import com.lakmalz.expensetracker.data.db.entity.TransactionsData

class TransactionRepository(applicationContext: Application) {
    private lateinit var transactionsDataDao: TransactionsDataDao
    private lateinit var accountDataDao: AccountDataDao
    private lateinit var incomeCatDataDao: IncomeCatDataDao
    private lateinit var expenseCatDataDao: ExpenseCatDataDao

    companion object {
        @Volatile
        private var INSTANCE: TransactionRepository? = null

        fun getInstance(applicationContext: Application): TransactionRepository {
            return INSTANCE ?: TransactionRepository(applicationContext)
        }
    }

    init{
        val database: AppDatabase? = AppDatabase.getInstance(applicationContext.applicationContext)
        transactionsDataDao = database!!.getTransactionsDataDao()
        accountDataDao = database!!.getAccountsDataDao()
        incomeCatDataDao = database!!.getIncomeCatDataDao()
        expenseCatDataDao = database!!.getExpenseCatDataDao()
    }

    fun insert(entity: TransactionsData) {
        AsyncTask.execute {
            transactionsDataDao.insert(entity)
        }
    }

    fun getAll(): LiveData<List<TransactionsData>>{
        return transactionsDataDao.getAll()
    }

    fun getAllByAccountId(accId: Int): LiveData<List<TransactionsData>>{
        return transactionsDataDao.getAllByAccountId(accId)
    }

    fun getBalance(accId: Int): LiveData<Double>{
        return transactionsDataDao.getBalance(accId)
    }

    fun delete(item: TransactionsData) {
        AsyncTask.execute {
            transactionsDataDao.delete(item)
        }
    }

    fun updateAccountType(item: AccountsData) {
        AsyncTask.execute {
            accountDataDao.update(item)
        }
    }

    fun updateIncomeCatType(item: IncomeCatData) {
        AsyncTask.execute {
            incomeCatDataDao.update(item)
        }
    }

    fun updateExpenseCatType(item: ExpenseCatData) {
        AsyncTask.execute {
            expenseCatDataDao.update(item)
        }
    }
}