package com.lakmalz.expensetracker.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lakmalz.expensetracker.data.db.entity.ExpenseCatData
@Dao
interface ExpenseCatDataDao {
    @Query("SELECT * FROM ExpenseCatData ORDER BY id DESC")
    fun getAll(): LiveData<List<ExpenseCatData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(accountsData: ExpenseCatData)

    @Update
    fun update(accountsData: ExpenseCatData)

    @Delete
    fun delete(accountsData: ExpenseCatData)
}