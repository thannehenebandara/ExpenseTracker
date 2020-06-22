package com.lakmalz.expensetracker.data.db.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExpenseCatData(
    var name: String?,
    var isActive: Boolean?
) :Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    ) {
        id = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeValue(isActive)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ExpenseCatData> {
        override fun createFromParcel(parcel: Parcel): ExpenseCatData {
            return ExpenseCatData(parcel)
        }

        override fun newArray(size: Int): Array<ExpenseCatData?> {
            return arrayOfNulls(size)
        }
    }

}