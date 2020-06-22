package com.lakmalz.expensetracker.data.db.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class TransactionsData() {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var accId: Int? = null
    var catId: Int? = null
    var catName: String? = null
    var isIncome: Boolean = false
    var amount: Double? = null
    var currency: String? = null
    var timestamp: Long? = null
}