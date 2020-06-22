package com.lakmalz.expensetracker.views.addnewtransaction.incomecategory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.lakmalz.expensetracker.data.db.entity.IncomeCatData
import com.lakmalz.expensetracker.repository.IncomeRepository

class IncomeListViewModel(application: Application) : AndroidViewModel(application) {
    private var incomeRepository: IncomeRepository = IncomeRepository.getInstance(application)

    fun insert(entity: IncomeCatData) {
        incomeRepository.insert(entity)
    }

    fun delete(entity: IncomeCatData) {
        incomeRepository.delete(entity)
    }

    fun update(entity: IncomeCatData) {
        incomeRepository.update(entity)
    }

    fun getAll(): LiveData<List<IncomeCatData>> {
        return incomeRepository.getAll()
    }
}