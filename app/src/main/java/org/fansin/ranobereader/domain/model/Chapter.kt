package org.fansin.ranobereader.domain.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "chapter")
data class Chapter(
    val id: Int,
    val title: String
) : Parcelable {
    override fun toString(): String {
        return "$id, $title"
    }
}
