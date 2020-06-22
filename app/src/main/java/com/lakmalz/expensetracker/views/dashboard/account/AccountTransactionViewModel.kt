package com.lakmalz.expensetracker.views.dashboard.account

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.lakmalz.expensetracker.data.db.entity.TransactionsData
import com.lakmalz.expensetracker.repository.TransactionRepository

class AccountTransactionViewModel(application: Application) : AndroidViewModel(application) {
    private var transactionRepository: TransactionRepository = TransactionRepository.getInstance(application)

    fun getAllByAccountId(accId: Int): LiveData<List<TransactionsData>> {
        return transactionRepository.getAllByAccountId(accId)
    }

    fun getBalance(accId: Int): LiveData<Double> {
        return transactionRepository.getBalance(accId)
    }

    fun delete(item: TransactionsData) {
        transactionRepository.delete(item)
    }
}