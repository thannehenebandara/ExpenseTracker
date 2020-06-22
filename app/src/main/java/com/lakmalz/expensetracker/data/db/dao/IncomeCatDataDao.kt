package com.lakmalz.expensetracker.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lakmalz.expensetracker.data.db.entity.IncomeCatData
@Dao
interface IncomeCatDataDao {
    @Query("SELECT * FROM IncomeCatData ORDER BY id DESC")
    fun getAll(): LiveData<List<IncomeCatData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(accountsData: IncomeCatData)

    @Update
    fun update(accountsData: IncomeCatData)

    @Delete
    fun delete(accountsData: IncomeCatData)
}