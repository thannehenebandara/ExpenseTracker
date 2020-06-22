package com.lakmalz.expensetracker.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.lakmalz.expensetracker.data.db.entity.TransactionsData

@Dao
interface TransactionsDataDao {
    @Query("SELECT * FROM TransactionsData")
    fun getAll(): LiveData<List<TransactionsData>>

    @Query("SELECT * FROM TransactionsData WHERE accId =:accId  ORDER BY timestamp DESC")
    fun getAllByAccountId(accId: Int): LiveData<List<TransactionsData>>

    @Query("SELECT SUM(amount) FROM TransactionsData WHERE accId =:accId")
    fun getBalance(accId: Int): LiveData<Double>

    @Insert
    fun insert(transactionsData: TransactionsData)

    @Delete
    fun delete(transactionsData: TransactionsData)
}