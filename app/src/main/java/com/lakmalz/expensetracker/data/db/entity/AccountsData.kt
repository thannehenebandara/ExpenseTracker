package com.lakmalz.expensetracker.data.db.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AccountsData(
    var name: String?,
    var isActive: Boolean?
): Parcelable{
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

    companion object CREATOR : Parcelable.Creator<AccountsData> {
        override fun createFromParcel(parcel: Parcel): AccountsData {
            return AccountsData(parcel)
        }

        override fun newArray(size: Int): Array<AccountsData?> {
            return arrayOfNulls(size)
        }
    }


}