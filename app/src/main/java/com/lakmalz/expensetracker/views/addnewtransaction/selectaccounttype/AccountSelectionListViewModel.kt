package com.lakmalz.expensetracker.views.addnewtransaction.selectaccounttype

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.lakmalz.expensetracker.data.db.entity.AccountsData
import com.lakmalz.expensetracker.repository.AccountRepository

class AccountSelectionListViewModel(application: Application) : AndroidViewModel(application) {
    private var accountRepository: AccountRepository = AccountRepository.getInstance(application)

    fun insert(entity: AccountsData) {
        accountRepository.insert(entity)
    }

    fun delete(entity: AccountsData) {
        accountRepository.delete(entity)
    }

    fun update(entity: AccountsData) {
        accountRepository.update(entity)
    }

    fun getAll(): LiveData<List<AccountsData>> {
        return accountRepository.getAll()
    }
}