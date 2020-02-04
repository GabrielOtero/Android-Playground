package com.example.notes.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "notes")
data class Note(
    @ColumnInfo(name = "title") var title: String? = "",
    @ColumnInfo(name = "content") var content: String? = "",
    @ColumnInfo(name = "timeStamp") var timeStamp: String? = ""
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
