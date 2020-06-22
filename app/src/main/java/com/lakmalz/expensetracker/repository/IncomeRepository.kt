package com.lakmalz.expensetracker.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.lakmalz.expensetracker.data.db.appdatabase.AppDatabase
import com.lakmalz.expensetracker.data.db.dao.IncomeCatDataDao
import com.lakmalz.expensetracker.data.db.entity.IncomeCatData

class IncomeRepository(applicationContext: Application) {
    private lateinit var incomeCatDataDao: IncomeCatDataDao

    companion object {
        @Volatile
        private var INSTANCE: IncomeRepository? = null

        fun getInstance(applicationContext: Application): IncomeRepository {
            return INSTANCE ?: IncomeRepository(applicationContext)
        }
    }

    init{
        val database: AppDatabase? = AppDatabase.getInstance(applicationContext.applicationContext)
        incomeCatDataDao = database!!.getIncomeCatDataDao()
    }

    fun insert(entity: IncomeCatData) {
        AsyncTask.execute {
            incomeCatDataDao.insert(entity)
        }
    }

    fun delete(entity: IncomeCatData) {
        AsyncTask.execute {
            incomeCatDataDao.delete(entity)
        }
    }

    fun update(entity: IncomeCatData) {
        AsyncTask.execute {
            incomeCatDataDao.update(entity)
        }
    }

    fun getAll(): LiveData<List<IncomeCatData>>{
        return incomeCatDataDao.getAll()
    }
}