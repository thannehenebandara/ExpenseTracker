package com.lakmalz.expensetracker.views.addnewtransaction.expensecategory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.lakmalz.expensetracker.data.db.entity.ExpenseCatData
import com.lakmalz.expensetracker.repository.ExpenseRepository

class ExpenseListViewModel(application: Application) : AndroidViewModel(application) {
    private var expenseRepository: ExpenseRepository = ExpenseRepository.getInstance(application)

    fun insert(entity: ExpenseCatData) {
        expenseRepository.insert(entity)
    }

    fun delete(entity: ExpenseCatData) {
        expenseRepository.delete(entity)
    }

    fun update(entity: ExpenseCatData) {
        expenseRepository.update(entity)
    }

    fun getAll(): LiveData<List<ExpenseCatData>> {
        return expenseRepository.getAll()
    }
}