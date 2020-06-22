package com.lakmalz.expensetracker.views.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.lakmalz.expensetracker.data.db.entity.AccountsData
import com.lakmalz.expensetracker.repository.AccountRepository

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    var selectedItem = 0

    private var accountRepository: AccountRepository = AccountRepository.getInstance(application)

    fun insert(entity: AccountsData) {
        accountRepository.insert(entity)
    }

    fun getAll(): LiveData<List<AccountsData>> {
        return accountRepository.getAll()
    }

    fun getAllASC(): LiveData<List<AccountsData>> {
        return accountRepository.getAllASC()
    }
}