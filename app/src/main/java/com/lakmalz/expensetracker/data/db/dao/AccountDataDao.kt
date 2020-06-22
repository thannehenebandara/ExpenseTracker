package com.lakmalz.expensetracker.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lakmalz.expensetracker.data.db.entity.AccountsData
@Dao
interface AccountDataDao {
    @Query("SELECT * FROM AccountsData ORDER BY id DESC")
    fun getAll(): LiveData<List<AccountsData>>

    @Query("SELECT * FROM AccountsData ORDER BY id ASC")
    fun getAllASC(): LiveData<List<AccountsData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(accountsData: AccountsData)

    @Update
    fun update(accountsData: AccountsData)

    @Delete
    fun delete(accountsData: AccountsData)
}