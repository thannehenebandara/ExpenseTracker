package com.lakmalz.expensetracker.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.lakmalz.expensetracker.data.db.appdatabase.AppDatabase
import com.lakmalz.expensetracker.data.db.dao.AccountDataDao
import com.lakmalz.expensetracker.data.db.entity.AccountsData

class AccountRepository(applicationContext: Application) {
    private lateinit var accountDataDAO: AccountDataDao

    companion object {
        @Volatile
        private var INSTANCE: AccountRepository? = null

        fun getInstance(applicationContext: Application): AccountRepository {
            return INSTANCE ?: AccountRepository(applicationContext)
        }
    }

    init{
        val database: AppDatabase? = AppDatabase.getInstance(applicationContext.applicationContext)
        accountDataDAO = database!!.getAccountsDataDao()
    }

    fun insert(entity: AccountsData) {
        AsyncTask.execute {
            accountDataDAO.insert(entity)
        }
    }

    fun delete(entity: AccountsData) {
        AsyncTask.execute {
            accountDataDAO.delete(entity)
        }
    }

    fun update(entity: AccountsData) {
        AsyncTask.execute {
            accountDataDAO.update(entity)
        }
    }

    fun getAll(): LiveData<List<AccountsData>>{
        return accountDataDAO.getAll()
    }

    fun getAllASC(): LiveData<List<AccountsData>>{
        return accountDataDAO.getAllASC()
    }
}